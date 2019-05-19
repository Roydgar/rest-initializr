package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

public interface SQLParserService {

    SQLTable parseCreateQuery(String query, SQLDialect sqlDialect);

}
