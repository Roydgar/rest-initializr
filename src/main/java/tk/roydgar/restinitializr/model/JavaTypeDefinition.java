package tk.roydgar.restinitializr.model;

import lombok.Data;
import tk.roydgar.restinitializr.model.enums.JavaType;

@Data
public class JavaTypeDefinition {
    private JavaType javaType;
    private String label;
}
