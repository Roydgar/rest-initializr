package tk.roydgar.restinitializr.model;

import lombok.Data;

@Data
public class PropertiesParameters {

    private String dataSourceUrl;
    private String dataSourceUsername;
    private String dataSourcePassword;
    private String dataSourceDriverClassName;
    private String serverPort;

}
