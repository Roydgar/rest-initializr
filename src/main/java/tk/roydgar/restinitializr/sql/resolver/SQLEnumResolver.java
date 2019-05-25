package tk.roydgar.restinitializr.sql.resolver;

import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.sql.model.SQLEnum;
import tk.roydgar.restinitializr.util.FormatUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SQLEnumResolver {

    private static final String ENUM = "enum";

    public Optional<SQLEnum> execute(SQLColumnDefinition sqlColumnDefinition) {
        SQLDataType dataType = sqlColumnDefinition.getDataType();

        if (dataType.getName().equalsIgnoreCase(ENUM)) {
            log.debug("Creating enum definition from column {}", sqlColumnDefinition);

            List<String> values = dataType.getArguments().stream()
                    .map(value -> (SQLCharExpr)value)
                    .map(SQLCharExpr::getText)
                    .collect(Collectors.toList());

            SQLEnum sqlEnum = new SQLEnum();
            String nameInCamelCase = FormatUtils.snakeToCamelCase(sqlColumnDefinition.getNameAsString());
            sqlEnum.setName(nameInCamelCase);
            sqlEnum.setValues(values);

            log.debug("Created enum enum definition from column {} is: {}", sqlColumnDefinition, sqlEnum);
            return Optional.of(sqlEnum);
        }
        return Optional.empty();
    }
}
