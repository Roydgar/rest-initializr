package tk.roydgar.restinitializr.model;

import lombok.Data;

@Data
public class PropertiesParameters {

    private String serverPort;
    private DataSourceParameters dataSourceParameters;

    @Data
    public static class DataSourceParameters {
        private String url;
        private String driverClassName;
        private String username;
        private String password;
    }

}
