package tk.roydgar.restinitializr.config.properties.sql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.roydgar.restinitializr.sql.model.SQLType;

import java.util.Map;

@ConfigurationProperties(prefix = "mysql")
@Getter
@Setter
public class MySQLTypeMappingProperties implements SQLTypeMappingProperties {

    private Map<String, SQLType> sqlTypeMap;

}
