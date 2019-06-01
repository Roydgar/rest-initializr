package tk.roydgar.restinitializr.ui.gui.validator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResult {
    private boolean success;
    private String message;
}
