package tk.roydgar.restinitializr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaType {
    private String label;
    private String importPath;
    private boolean isPrimitive;
    private boolean isLang;
}
