package tk.roydgar.restinitializr.model;

import lombok.Data;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

@Data
public class PropertiesParameters {

    private String serverPort;
    private DataSourceParameters dataSourceParameters;

    @Data
    public static class DataSourceParameters {
        private String dialect;
        private String host;
        private String port;
        private String dbName;
        private String serverTimeZone;
        private boolean useUnicode;
        private boolean createTableIfNotExist;
        private SQLDialect sqlDialect;

        private String username;
        private String password;
    }

}
