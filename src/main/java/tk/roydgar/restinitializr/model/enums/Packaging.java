package tk.roydgar.restinitializr.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Packaging {
    JAR("jar"),
    WAR("war");

    private String label;
}
