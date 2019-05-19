package tk.roydgar.restinitializr.model;

import lombok.Data;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

import java.util.List;

@Data
public class GeneratorParameters {

    private SpringInitializrParameters initializrParameters;
    private SQLDialect sqlDialect;
    private PropertiesParameters propertiesParameters;
    private List<String> sqlQueries;
}
