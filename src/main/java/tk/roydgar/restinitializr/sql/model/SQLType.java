package tk.roydgar.restinitializr.sql.model;

import lombok.Getter;
import lombok.Setter;
import tk.roydgar.restinitializr.model.enums.JavaType;
import tk.roydgar.restinitializr.sql.model.enums.SQLTypeClassification;

@Getter
@Setter
public class SQLType {
    private SQLTypeClassification typeClassification;
    private JavaType javaType;
}
