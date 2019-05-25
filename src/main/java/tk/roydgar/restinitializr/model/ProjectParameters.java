package tk.roydgar.restinitializr.model;

import lombok.Data;
import tk.roydgar.restinitializr.model.enums.AutomationBuildSystem;
import tk.roydgar.restinitializr.model.enums.Packaging;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

@Data
public class ProjectParameters {

    private String groupId;
    private String artifactId;
    private String language;
    private Packaging packaging;
    private String javaVersion;
    private String description;
    private AutomationBuildSystem automationBuildSystem;
    private SQLDialect sqlDialect;

}
