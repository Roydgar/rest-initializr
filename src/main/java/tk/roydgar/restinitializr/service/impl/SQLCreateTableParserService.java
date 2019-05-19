package tk.roydgar.restinitializr.service.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.service.SQLParserService;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.sql.visitor.SQLTableVisitor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQLCreateTableParserService implements SQLParserService {

    private final List<SQLTableVisitor> queryProcessingVisitors;

    @Override
    public SQLTable parseCreateQuery(String query, SQLDialect sqlDialect) {
        log.debug("Parsing sql query: {} for sql type: {}", query, sqlDialect.getJdbcConstantName());

        SQLStatement sqlStatement = SQLUtils.parseSingleStatement(query, sqlDialect.getJdbcConstantName());
        SQLTable sqlTable = new SQLTable();

        queryProcessingVisitors
                .forEach(sqlVisitor -> {
                    sqlVisitor.setTable(sqlTable);
                    sqlVisitor.setSqlDialect(sqlDialect);
                    log.info("Applying visitor {} to parsed statement", sqlVisitor.getClass().getSimpleName());
                    sqlStatement.accept(sqlVisitor);
                });

        return sqlTable;
    }

}
