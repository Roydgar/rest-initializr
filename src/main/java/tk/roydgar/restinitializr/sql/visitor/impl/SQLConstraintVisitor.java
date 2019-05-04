package tk.roydgar.restinitializr.sql.visitor.impl;

import com.alibaba.druid.sql.ast.statement.SQLColumnPrimaryKey;
import com.alibaba.druid.sql.ast.statement.SQLColumnUniqueKey;
import com.alibaba.druid.sql.ast.statement.SQLNotNullConstraint;
import com.alibaba.druid.sql.ast.statement.SQLNullConstraint;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import lombok.Getter;

@Getter
public class SQLConstraintVisitor extends SQLASTVisitorAdapter {

    private boolean isNullable = true;
    private boolean isUnique;
    private boolean isPrimaryKey;

    @Override
    public boolean visit(SQLNotNullConstraint x) {
        isNullable = false;
        return true;
    }

    @Override
    public boolean visit(SQLNullConstraint x) {
        isNullable = true;
        return true;
    }

    @Override
    public boolean visit(SQLColumnUniqueKey x) {
        isUnique = true;
        return true;
    }

    @Override
    public boolean visit(SQLColumnPrimaryKey x) {
        isPrimaryKey = true;
        return true;
    }
}
