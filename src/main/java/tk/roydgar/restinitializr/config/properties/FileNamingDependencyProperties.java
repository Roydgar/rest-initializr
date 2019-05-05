package tk.roydgar.restinitializr.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import java.util.Map;

@ConfigurationProperties(prefix = "file-naming-dependencies")
@Getter
@Setter
public class FileNamingDependencyProperties {

    private Map<TemplateType, TemplateKey> templateTypeTemplateKeyMap;

}
