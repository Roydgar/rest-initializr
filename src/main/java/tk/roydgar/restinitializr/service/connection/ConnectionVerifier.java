package tk.roydgar.restinitializr.service.connection;

import tk.roydgar.restinitializr.model.PropertiesParameters;

import java.sql.SQLException;

public interface ConnectionVerifier {
    void verifyConnection(PropertiesParameters.DataSourceParameters dataSourceParameters) throws SQLException, ClassNotFoundException;
}
