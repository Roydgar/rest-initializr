package tk.roydgar.restinitializr.model;

import lombok.Data;

import java.util.List;

@Data
public class GeneratorParameters {

    private ProjectParameters projectParameters;
    private PropertiesParameters propertiesParameters;
    private List<String> sqlQueries;
}
