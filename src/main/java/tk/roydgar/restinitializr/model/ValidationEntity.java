package tk.roydgar.restinitializr.model;

import lombok.Data;
import tk.roydgar.restinitializr.model.enums.ValidationAnnotation;

@Data
public class ValidationEntity {

    private ValidationAnnotation validationAnnotation;
    private Object value;

}
