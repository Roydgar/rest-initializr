package tk.roydgar.restinitializr.sql.model.enums;

import com.alibaba.druid.util.JdbcConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  SQLDialect {

    MY_SQL(JdbcConstants.MYSQL, JdbcConstants.MYSQL_DRIVER),
    POSTGRES_SQL(JdbcConstants.POSTGRESQL, JdbcConstants.POSTGRESQL_DRIVER),
    SQL_SERVER(JdbcConstants.SQL_SERVER, JdbcConstants.SQL_SERVER_DRIVER);

    private String jdbcConstantName;
    private String driverName;

}
