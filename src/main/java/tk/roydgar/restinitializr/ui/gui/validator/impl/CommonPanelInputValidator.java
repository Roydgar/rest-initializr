package tk.roydgar.restinitializr.ui.gui.validator.impl;

import org.apache.logging.log4j.util.Strings;
import tk.roydgar.restinitializr.ui.gui.validator.PanelInputValidator;
import tk.roydgar.restinitializr.ui.gui.validator.UserInputValidator;
import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommonPanelInputValidator implements PanelInputValidator {

    protected List<UserInputValidator> validators = new ArrayList<>();

    @Override
    public ValidationResult execute() {
        List<String> validationMessages = validators.stream()
                .map(UserInputValidator::execute)
                .filter(validationResult -> !validationResult.isSuccess())
                .map(ValidationResult::getMessage)
                .collect(Collectors.toList());
        return new ValidationResult(validationMessages.isEmpty(), Strings.join(validationMessages, '\n'));
    }

}
