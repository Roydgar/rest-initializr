package tk.roydgar.restinitializr.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@ConfigurationProperties(prefix = "template")
@Validated
@Getter
@Setter
public class TemplateProperties {

    @NotBlank
    private String templatesRelativePath;
    @NotBlank
    private String templateExtension;

    @NotEmpty
    private Map<TemplateKey,@NotBlank String> keyResolvers;
    @NotEmpty
    private Map<TemplateType,@NotBlank String> fileNameResolvers;

}
