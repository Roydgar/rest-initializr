package tk.roydgar.restinitializr.service.modifier;

import tk.roydgar.restinitializr.sql.model.SQLTable;

public interface SQLTableModifier {

    void execute(SQLTable sqlTable);

}
