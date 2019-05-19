package tk.roydgar.restinitializr.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Getter
public class ExtendableZipFile implements AutoCloseable {

    private final static String DEFAULT_TEMP_FILE_EXTENSION = ".zip";

    private File temporaryZipFile;
    private ZipFile zipFile;

    public ExtendableZipFile(String tempDirectory, byte[] zipContent) throws IOException, ZipException {
        this(tempDirectory, zipContent, DEFAULT_TEMP_FILE_EXTENSION);
    }

    public ExtendableZipFile(String tempDirectory, byte[] zipContent, String extension) throws IOException, ZipException {
        String temporaryFileName = createTemporaryFileName(tempDirectory, extension);
        log.debug("Creating temporary zip file with name {}", tempDirectory);

        this.temporaryZipFile = new File(temporaryFileName);
        FileUtils.writeByteArrayToFile(temporaryZipFile, zipContent);

        this.zipFile = new ZipFile(temporaryZipFile);
    }

    private String createTemporaryFileName(String tempDirectory, String extension) {
        return tempDirectory + File.separator + UUID.randomUUID() + "." + extension;
    }

    public void addFileToZip(InputStream fileStream, String fileName) throws ZipException{
        log.debug("Adding new file to zip: {}", fileName);

        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setSourceExternalStream(true);
        zipParameters.setFileNameInZip(fileName);

        zipFile.addStream(fileStream, zipParameters);
    }

    public void addFileToZip(String fileNameInZip, File file) throws ZipException{
        log.debug("Adding new file to zip: {}", file.getName());

        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setSourceExternalStream(true);
        zipParameters.setFileNameInZip(fileNameInZip);

        zipFile.addFile(file, zipParameters);
    }

    public void removeFromZip(String fileName) throws ZipException {
        zipFile.removeFile(fileName);
    }

    public void extractFile(String fileName, String location) throws ZipException {
        FileHeader fileHeader = new FileHeader();
        fileHeader.setFileName(fileName);
        zipFile.extractFile(fileHeader, location);
    }

    public byte[] toByteArray() throws IOException {
        return FileUtils.readFileToByteArray(temporaryZipFile);
    }

    public void unzipTo(String location) throws ZipException {
        log.debug("Unzipping to {}", location);

        UnzipParameters unzipParameters = new UnzipParameters();
        unzipParameters.setIgnoreAllFileAttributes(true);

        zipFile.extractAll(location);
    }

    @Override
    public void close() throws IOException {
        FileUtils.forceDelete(this.temporaryZipFile);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        FileUtils.deleteQuietly(this.temporaryZipFile);
    }

}
