package tk.roydgar.restinitializr.model;

import lombok.Data;

@Data
public class GeneratorParameters {

    private ProjectParameters projectParameters;
    private PropertiesParameters propertiesParameters;
    private String outputDirectory;

}
