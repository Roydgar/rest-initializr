package tk.roydgar.restinitializr.config.properties.sql;

import tk.roydgar.restinitializr.sql.model.SQLType;

import java.util.Map;

public interface SQLTypeMappingProperties {
    Map<String, SQLType> getSqlTypeMap();
}
