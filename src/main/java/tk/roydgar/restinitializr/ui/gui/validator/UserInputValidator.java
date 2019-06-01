package tk.roydgar.restinitializr.ui.gui.validator;

import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;

@FunctionalInterface
public interface UserInputValidator {
    ValidationResult execute();
}
