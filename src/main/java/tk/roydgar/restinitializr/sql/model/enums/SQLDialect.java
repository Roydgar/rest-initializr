package tk.roydgar.restinitializr.sql.model.enums;

import com.alibaba.druid.util.JdbcConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  SQLDialect {

    MY_SQL(JdbcConstants.MYSQL);

    private String jdbcConstantName;

}
