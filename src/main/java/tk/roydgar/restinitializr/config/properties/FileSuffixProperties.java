package tk.roydgar.restinitializr.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import java.util.Map;

@ConfigurationProperties(prefix = "file-suffix")
@Validated
@Getter
@Setter
public class FileSuffixProperties {

    private Map<TemplateType, String> typeToSuffixMap;

}
