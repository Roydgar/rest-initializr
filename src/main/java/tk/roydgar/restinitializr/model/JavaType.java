package tk.roydgar.restinitializr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaType {
    private String label;
    private String importPath;

    public boolean isImportNeeded() {
        return importPath != null && !importPath.contains(".lang.");
    }
}
