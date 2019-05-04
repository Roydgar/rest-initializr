package tk.roydgar.restinitializr.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExtendableZipFileTest {

    private final static String TEST_TEMPORARY_DIRECTORY = "src" + File.separator + "test" + File.separator + "temp";

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private byte[] zipContent;
    private ExtendableZipFile subject;

    @Before
    public void setUp() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try(ZipOutputStream zos = new ZipOutputStream(byteArrayOutputStream)) {

            ZipEntry entry = new ZipEntry("test.txt");

            zos.putNextEntry(entry);
            zos.write("Hello".getBytes());
            zos.closeEntry();

        }

        this.zipContent = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        this.subject = new ExtendableZipFile(TEST_TEMPORARY_DIRECTORY, zipContent);
    }

    @After
    public void cleanUp() throws IOException {
        subject.close();
    }

    @AfterClass
    public static void deleteTempFolder() throws IOException {
        FileUtils.forceDelete(new File(TEST_TEMPORARY_DIRECTORY));
    }

    @Test
    public void constructor_createsTemporaryFile() {
        assertThat(new File(TEST_TEMPORARY_DIRECTORY).exists()).isTrue();
        assertThat(subject.getTemporaryZipFile().exists()).isTrue();
    }

    @Test
    public void addFileToZip_successful() throws Exception {
        String content = "Hello test";
        String testFileName = "test.txt";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());

        subject.addFileToZip(inputStream, testFileName);

        ZipFile zipFile = new ZipFile(subject.getTemporaryZipFile());
        ZipEntry testEntry = zipFile.getEntry(testFileName);

        try(InputStream zippedFileStream = zipFile.getInputStream(testEntry)) {
            String actualContent = IOUtils.toString(zippedFileStream, Charset.defaultCharset());
            softly.assertThat(actualContent).isEqualTo(content);
        }

        softly.assertThat(testEntry).isNotNull();
        softly.assertThat(testEntry.getName()).isEqualTo(testFileName);
        zipFile.close();
    }

    @Test
    public void toByteArray() throws Exception {
        byte[] actual = subject.toByteArray();
        assertThat(actual).isEqualTo(zipContent);
    }

    @Test
    public void unzipTo() throws Exception {
        String location = TEST_TEMPORARY_DIRECTORY + File.separator + "copy";
        File file = new File(location);

        subject.unzipTo(location);

        assertThat(file).exists();
        FileUtils.deleteQuietly(file);
    }

}