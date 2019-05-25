package tk.roydgar.restinitializr.sql.visitor.impl.mysql;

import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.sql.model.SQLColumn;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.sql.visitor.SQLTableVisitor;
import tk.roydgar.restinitializr.util.FormatUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@Slf4j
@Setter
public class MySQLTableVisitor extends MySqlASTVisitorAdapter implements SQLTableVisitor {

    private SQLTable table;
    private SQLDialect sqlDialect;

    @Override
    public boolean visit(MySqlPrimaryKey x) {
        List<String> referencedColumns = x.getColumns().stream()
                .map(column -> FormatUtils.deleteQuotes(column.getExpr().toString()))
                .collect(Collectors.toList());

        log.debug("Resolving primary key constraints for columns {}", referencedColumns);
        performIfFound(table.getColumns(), referencedColumns, sqlColumn -> sqlColumn.setPrimaryKey(true));

        return true;
    }

    @Override
    public boolean visit(MySqlUnique x) {
        List<String> referencedColumns = x.getColumns().stream()
                .map(column -> FormatUtils.deleteQuotes(column.getExpr().toString()))
                .collect(Collectors.toList());

        log.debug("Resolving unique constraints for columns {}", referencedColumns);
        performIfFound(table.getColumns(), referencedColumns, sqlColumn -> sqlColumn.setUnique(true));

        return true;
    }

    @Override
    public boolean visit(MysqlForeignKey x) {

        return true;
    }

    private void performIfFound(List<SQLColumn> sqlColumns, List<String> referencedColumns, Consumer<SQLColumn> consumer) {
        for (String referencedColumn : referencedColumns) {
            for (SQLColumn sqlColumn : sqlColumns) {
                if (sqlColumn.getName().equals(referencedColumn)) {
                    consumer.accept(sqlColumn);
                }
            }
        }
    }
}
