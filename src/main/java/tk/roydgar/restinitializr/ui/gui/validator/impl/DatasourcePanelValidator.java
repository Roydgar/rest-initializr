package tk.roydgar.restinitializr.ui.gui.validator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.GUIProperties;
import tk.roydgar.restinitializr.ui.gui.DataSourcePanel;
import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;

import javax.annotation.PostConstruct;

import static java.lang.String.format;
import static tk.roydgar.restinitializr.util.ValidationUtils.emptyOrLessThenSize;

@Component
@RequiredArgsConstructor
public class DatasourcePanelValidator extends CommonPanelInputValidator {

    private final DataSourcePanel dataSourcePanel;
    private final GUIProperties guiProperties;

    @PostConstruct
    public void initValidators() {
        int maxLength = guiProperties.getMaxStringInputLength();
        String emptyOrTooLongFormat = guiProperties.getEmptyOrTooLongFormat();

        validators.add(() -> new ValidationResult(emptyOrLessThenSize(dataSourcePanel.getDatasourceUrl(), maxLength),
                format(emptyOrTooLongFormat, "URL", maxLength)));
        validators.add(() -> new ValidationResult(emptyOrLessThenSize(dataSourcePanel.getDatasourceUsername(), maxLength),
                format(emptyOrTooLongFormat, "Username", maxLength)));
        validators.add(() -> new ValidationResult(emptyOrLessThenSize(dataSourcePanel.getDatasourcePassword(), maxLength),
                format(emptyOrTooLongFormat, "Password", maxLength)));
        validators.add(() -> new ValidationResult(emptyOrLessThenSize(dataSourcePanel.getDatasourceDriverClassName(), maxLength),
                format(emptyOrTooLongFormat, "Driver classname", maxLength)));
    }

}
