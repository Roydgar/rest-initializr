package tk.roydgar.restinitializr.service.impl.modifier;

import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.service.modifier.SQLTableModifier;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.util.FormatUtils;

@Component
public class FormattedValidationParametersSQLTableModifier implements SQLTableModifier {

    @Override
    public void execute(SQLTable sqlTable) {
        sqlTable.getColumns().stream()
                .filter(sqlColumn -> sqlColumn.getValidationEntities() != null)
                .flatMap(sqlColumn -> sqlColumn.getValidationEntities().stream())
                .forEach(validationEntity -> {
                    String formattedParameters = FormatUtils.formatValidationParameters(validationEntity.getValidationParameterEntities());
                    validationEntity.setFormattedParameters(formattedParameters);
                });
    }

}
