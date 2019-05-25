package tk.roydgar.restinitializr.service.impl.dependency;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.service.dependency.DependencyExtender;
import tk.roydgar.restinitializr.util.ExtendableZipFile;
import tk.roydgar.restinitializr.util.FileExtenderUtils;

import java.io.File;

@Component
public class DependencyExtenderImpl implements DependencyExtender {

    @Value("${zip-file.tempDir}")
    private String tempDirName;

    @Override
    @SneakyThrows
    public void addDependency(ExtendableZipFile extendableZipFile, String buildFileName, String dependenciesKeyword, String artifactName, String dependency) {
        String fileNameInZip = artifactName + File.separator + buildFileName;
        String tempFileName = tempDirName + File.separator + artifactName + File.separator + buildFileName;

        extendableZipFile.unzipTo(tempDirName);
        File resultContentFile = FileExtenderUtils.readAndAddDependency(tempFileName, dependenciesKeyword, dependency);

        extendableZipFile.removeFromZip(fileNameInZip);
        extendableZipFile.addFileToZip(fileNameInZip, resultContentFile);

        FileUtils.forceDelete(resultContentFile);
    }

}
