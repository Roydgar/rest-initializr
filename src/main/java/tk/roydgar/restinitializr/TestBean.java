package tk.roydgar.restinitializr;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "loh")
@Getter
@Setter
public class TestBean {
    private String value;
}
