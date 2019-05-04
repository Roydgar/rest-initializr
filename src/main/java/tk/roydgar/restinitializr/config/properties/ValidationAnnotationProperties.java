package tk.roydgar.restinitializr.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ConfigurationProperties(prefix = "validation")
@Validated
@Getter
@Setter
public class ValidationAnnotationProperties {

    private List<Annotation> annotations;

    @Getter
    @Setter
    public static class Annotation {
        private String label;
        private List<String> supportedTypes;
        private List<Parameter> parameterList;
    }

    @Getter
    @Setter
    public static class Parameter {
        private String label;
        private String type;
    }
}
