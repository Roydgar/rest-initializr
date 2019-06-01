package tk.roydgar.restinitializr.ui.gui.validator.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.GUIProperties;
import tk.roydgar.restinitializr.ui.gui.SQLQueriesPanel;
import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class SQLQueriesPanelValidator extends CommonPanelInputValidator {

    private final SQLQueriesPanel sqlQueriesPanel;
    private final GUIProperties guiProperties;

    @PostConstruct
    public void initValidators() {
        validators.add(() -> new ValidationResult(!StringUtils.isEmpty(sqlQueriesPanel.getSQLQueries()),
                format(guiProperties.getEmptyFormat(), "SQL queries")));
    }

}
