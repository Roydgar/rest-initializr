package tk.roydgar.restinitializr.model.validation;

import lombok.Data;
import tk.roydgar.restinitializr.model.enums.ValidationAnnotation;

import java.util.List;

@Data
public class ValidationEntity {

    private ValidationAnnotation validationAnnotation;
    private List<ValidationParameterEntity> validationParameterEntities;
    private String formattedParameters;

}
