package tk.roydgar.restinitializr.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import java.util.Map;

@ConfigurationProperties(prefix = "file-extension")
@Getter
@Setter
public class FileExtensionProperties {

    private Map<TemplateType, String> typeToFileExtensionMap;

}
