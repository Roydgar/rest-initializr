package tk.roydgar.restinitializr.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file-extension")
@Getter
@Setter
public class GUIProperties {

    private int windowWidth;
    private int windowHeight;
    private String windowTitle;

}
