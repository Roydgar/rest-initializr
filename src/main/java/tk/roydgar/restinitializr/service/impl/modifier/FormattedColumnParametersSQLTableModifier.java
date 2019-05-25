package tk.roydgar.restinitializr.service.impl.modifier;

import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.service.modifier.SQLTableModifier;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.util.FormatUtils;

@Component
public class FormattedColumnParametersSQLTableModifier implements SQLTableModifier {

    @Override
    public void execute(SQLTable sqlTable) {
        sqlTable.getColumns().stream()
                .forEach(sqlColumn -> {
                    String formattedParameters = FormatUtils.formatColumnParameters(sqlColumn);
                    sqlColumn.setFormattedParameters(formattedParameters);
                });
    }

}
