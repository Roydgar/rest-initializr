package tk.roydgar.restinitializr.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class SpringInitializrParameters {

    private String groupId;
    private String artifactId;
    private String type;
    private String language;
    private String bootVersion;
    private String packaging;
    private String javaVersion;
    private String description;

    private List<String> dependencies = new ArrayList<>();

}
