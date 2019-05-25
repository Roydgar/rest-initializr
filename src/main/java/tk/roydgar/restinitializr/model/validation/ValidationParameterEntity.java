package tk.roydgar.restinitializr.model.validation;

import lombok.Data;
import tk.roydgar.restinitializr.model.enums.ValidationAnnotation;

@Data
public class ValidationParameterEntity {
    private ValidationAnnotation.Parameter parameter;
    private Object value;
}
