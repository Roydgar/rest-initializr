package tk.roydgar.restinitializr.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@ConfigurationProperties(prefix = "package-naming")
@Validated
@Getter
@Setter
public class PackageNamingProperties {

    @NotBlank
    private String defaultProjectPath;
    @NotBlank
    private String resourcesPath;
    @NotBlank
    private String customPackageSeparator;

    private Map<TemplateType, String> typeToPackageNameMap;

}
