package tk.roydgar.restinitializr.service.resolver;

import net.lingala.zip4j.exception.ZipException;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

public interface EnumResolver {

    void resolveEnums(SQLTable sqlTable, ExtendableZipFile extendableZipFile, SpringInitializrParameters initializrParameters) throws ZipException;

}
