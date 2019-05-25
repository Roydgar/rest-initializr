package tk.roydgar.restinitializr.sql.parser;

import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

public interface SQLParser {

    SQLTable parseCreateQuery(String query, SQLDialect sqlDialect);

}
