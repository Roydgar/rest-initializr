package tk.roydgar.restinitializr.sql.model;

import lombok.Data;
import tk.roydgar.restinitializr.model.JavaTypeDefinition;
import tk.roydgar.restinitializr.model.validation.ValidationEntity;

import java.util.ArrayList;
import java.util.List;

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

    private JavaTypeDefinition javaTypeDefinition;
    private List<ValidationEntity> validationEntities = new ArrayList<>();

    private String formattedParameters;

}
