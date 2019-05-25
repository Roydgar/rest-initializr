package tk.roydgar.restinitializr.service.resolver;

import net.lingala.zip4j.exception.ZipException;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

public interface ResourceResolver {

    void resolveResourceFiles(ExtendableZipFile extendableZipFile, SpringInitializrParameters initializrParameters,
                              PropertiesParameters propertiesParameters) throws ZipException;

}
