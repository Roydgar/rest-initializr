package tk.roydgar.restinitializr.model;

import lombok.Data;

@Data
public class GeneratorRestParameters {

    private ProjectParameters projectParameters;
    private PropertiesParameters propertiesParameters;
    private SQLInputQueries sqlInputQueries;

}
