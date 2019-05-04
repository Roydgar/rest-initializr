package tk.roydgar.restinitializr.sql.model;

import lombok.Data;
import tk.roydgar.restinitializr.config.properties.sql.MySQLTypeMappingProperties;
import tk.roydgar.restinitializr.model.JavaType;

@Data
public class SQLColumn {

    private String name;
    private String dataType;

    private boolean nullable = true;
    private boolean autoIncremental;
    private boolean unique;

    private Number precision;
    private Number scale;
    private Number length;
    private String defaultValue;

    private boolean isPrimaryKey;
    private boolean isForeignKey;

    private JavaType javaType;
}
