package tk.roydgar.restinitializr.service.impl.connection;

import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.service.connection.ConnectionVerifier;

import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionVerifierImpl implements ConnectionVerifier {

    @Override
    public void verifyConnection(PropertiesParameters.DataSourceParameters dataSourceParameters) throws SQLException, ClassNotFoundException {
        Class.forName(dataSourceParameters.getDriverClassName());

        DriverManager.
                getConnection(dataSourceParameters.getUrl()
                        ,dataSourceParameters.getUsername(),dataSourceParameters.getPassword());
    }

}
