package tk.roydgar.restinitializr.sql.visitor.impl;

import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.sql.visitor.SQLTableVisitor;
import tk.roydgar.restinitializr.util.FormatUtils;

@Component
@Slf4j
@Getter
@Setter
public class SQLCreateTableVisitor extends SQLASTVisitorAdapter implements SQLTableVisitor {

    private SQLTable table;
    private SQLDialect sqlDialect;

    @Override
    public boolean visit(SQLCreateTableStatement x) {
        String name = x.getTableSource().getName().getSimpleName();
        log.debug("Parsed table name: {}", name);

        table.setName(FormatUtils.deleteQuotes(name));
        return true;
    }

}
