package tk.roydgar.restinitializr.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AutomationBuildSystem {

    MAVEN("maven-project"),
    GRADLE("gradle-project");

    private String label;

}
