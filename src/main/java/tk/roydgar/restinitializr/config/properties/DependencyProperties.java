package tk.roydgar.restinitializr.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "automation-build")
@Getter
@Setter
public class DependencyProperties {

    private List<String> mavenDependencies;
    private List<String> gradleDependencies;

}
