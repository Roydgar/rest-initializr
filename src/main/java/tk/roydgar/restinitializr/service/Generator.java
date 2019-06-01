package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.SQLInputQueries;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.util.List;

public interface Generator {

    List<SQLTable> parseQueries(SQLInputQueries sqlInputQueries);
    ExtendableZipFile generate(GeneratorParameters generatorParameters, List<SQLTable> sqlTables);

}
