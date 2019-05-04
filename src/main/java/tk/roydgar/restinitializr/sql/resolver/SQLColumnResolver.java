package tk.roydgar.restinitializr.sql.resolver;

import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.expr.SQLValuableExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnConstraint;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.sql.SQLTypeMappingProperties;
import tk.roydgar.restinitializr.exception.CannotParseSQLTypeException;
import tk.roydgar.restinitializr.sql.model.SQLColumn;
import tk.roydgar.restinitializr.sql.model.SQLType;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.sql.model.enums.SQLTypeClassification;
import tk.roydgar.restinitializr.sql.visitor.impl.SQLConstraintVisitor;
import tk.roydgar.restinitializr.util.FormatUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class SQLColumnResolver {

    private final Map<SQLDialect, SQLTypeMappingProperties> dialectToMappingPropertiesMap;

    public SQLColumn execute(SQLColumnDefinition sqlColumnDefinition, SQLDialect sqlDialect) {
        log.info("Resolving column {} for dialect {}", sqlColumnDefinition.getNameAsString(), sqlDialect);
        SQLTypeMappingProperties sqlTypeMappingProperties = dialectToMappingPropertiesMap.get(sqlDialect);
        SQLColumn column = new SQLColumn();

        column.setName(FormatUtils.deleteQuotes(sqlColumnDefinition.getNameAsString()));
        column.setAutoIncremental(sqlColumnDefinition.isAutoIncrement());
        column.setDataType(parseType(sqlColumnDefinition));

        SQLType sqlType = sqlTypeMappingProperties.getSqlTypeMap().get(column.getDataType());
        if (sqlType == null) {
            throw new CannotParseSQLTypeException(String.format("Cannot parse sql type %s. " +
                    "Provided type doesnt exist in configuration.", column.getDataType()));
        }

        parseArguments(sqlColumnDefinition, column, sqlType.getTypeClassification());
        parseDefaultValue(sqlColumnDefinition, column);

        sqlColumnDefinition.getConstraints()
                .forEach(constraint -> parseConstraints(column, constraint));

        column.setJavaType(sqlType.getJavaType());
        return column;
    }

    private void parseConstraints(SQLColumn column, SQLColumnConstraint constraint) {
        SQLConstraintVisitor sqlConstraintVisitor = new SQLConstraintVisitor();
        log.info("Applying sql constraints for column {}", column.getName());
        constraint.accept(sqlConstraintVisitor);

        column.setNullable(sqlConstraintVisitor.isNullable());
        column.setUnique(sqlConstraintVisitor.isUnique());
        column.setPrimaryKey(sqlConstraintVisitor.isPrimaryKey());
    }

    private void parseDefaultValue(SQLColumnDefinition sqlColumnDefinition, SQLColumn column) {
        Optional.ofNullable(sqlColumnDefinition.getDefaultExpr())
                .ifPresent(sqlExpr -> {
                    SQLValuableExpr expression = (SQLValuableExpr)sqlExpr;
                    String value = FormatUtils.deleteQuotes(expression.getValue().toString());

                    log.debug("Setting default value for column {}: {}", sqlColumnDefinition, value);
                    column.setDefaultValue(value);
                });
    }

    private void parseArguments(SQLColumnDefinition sqlColumnDefinition, SQLColumn column,
                                SQLTypeClassification columnTypeClassification) {

        if (!(columnTypeClassification == SQLTypeClassification.TEXT ||
                columnTypeClassification == SQLTypeClassification.NUMERIC)) {
            log.debug("Column {} has no arguments", column.getName());
            return;
        }

        SQLDataType dataType = sqlColumnDefinition.getDataType();
        List<Number> arguments = dataType.getArguments().stream()
                .map(argument -> {
                    SQLIntegerExpr integerExpr = (SQLIntegerExpr)argument;
                    return integerExpr.getNumber();
                })
                .collect(Collectors.toList());

        if (arguments.size() == 1) {
            if (columnTypeClassification == SQLTypeClassification.TEXT) {
                log.debug("Setting length {} for column {}", arguments.get(0), column.getName());
                column.setLength(arguments.get(0));
            } else {
                log.debug("Setting precision {} for column {}", arguments.get(0), column.getName());
                column.setPrecision(arguments.get(0));
            }
        } else if (arguments.size() == 2 && columnTypeClassification == SQLTypeClassification.NUMERIC) {
            column.setPrecision(arguments.get(0));
            column.setScale(arguments.get(1));
            log.debug("Setting scale and precision for column {}", column, column.getName());

        }
    }

    private String parseType(SQLColumnDefinition sqlColumnDefinition) {
        return sqlColumnDefinition.getDataType().getName().toLowerCase();
    }
}
