package tk.roydgar.restinitializr.sql.visitor;

import com.alibaba.druid.sql.visitor.SQLASTVisitor;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

public interface SQLTableVisitor extends SQLASTVisitor {

    void setTable(SQLTable sqlTable);
    void setSqlDialect(SQLDialect sqlDialect);

}
