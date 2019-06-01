package tk.roydgar.restinitializr.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gui")
@Getter
@Setter
public class GUIProperties {

    private int windowWidth;
    private int windowHeight;
    private String windowTitle;
    private int defaultStringInputLength;
    private int maxStringInputLength;
    private String emptyFormat;
    private String emptyOrTooLongFormat;
    private String emptyOrDoesntMatchPatterFormat;
    private String javaVersionPattern;

}
