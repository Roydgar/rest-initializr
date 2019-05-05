package tk.roydgar.restinitializr.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import java.util.Map;

@ConfigurationProperties(prefix = "template-type-labels")
@Getter
@Setter
public class TemplateTypeLabelsProperties {

    private Map<TemplateType, String> templateTypeToLabelMap;

}
