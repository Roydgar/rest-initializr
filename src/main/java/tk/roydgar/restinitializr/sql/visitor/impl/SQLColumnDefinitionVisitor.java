package tk.roydgar.restinitializr.sql.visitor.impl;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.sql.model.SQLColumn;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.sql.resolver.SQLColumnResolver;
import tk.roydgar.restinitializr.sql.resolver.SQLEnumResolver;
import tk.roydgar.restinitializr.sql.visitor.SQLTableVisitor;

@Component
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class SQLColumnDefinitionVisitor extends SQLASTVisitorAdapter implements SQLTableVisitor {

    private final SQLColumnResolver columnResolver;
    private final SQLEnumResolver enumResolver;

    private SQLTable table;
    private SQLDialect sqlDialect;

    @Override
    public boolean visit(SQLColumnDefinition x) {
        SQLColumn sqlColumn = columnResolver.execute(x, sqlDialect);
        log.debug("Parsed column {}", sqlColumn);

        table.getColumns().add(sqlColumn);

        enumResolver.execute(x).ifPresent(table.getEnums()::add);
        return true;
    }

}
