package tk.roydgar.restinitializr.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "spring-initializr")
@Validated
@Getter
@Setter
public class SpringInitializrProperties {

    @NotBlank
    private String springBootVersion;
    private List<@NotEmpty String> dependencies;
    private Map<SQLDialect, @NotEmpty String> sqlDialectToDependencyMap;

}
