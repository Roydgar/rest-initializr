package tk.roydgar.restinitializr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

import java.util.List;

@Data
@AllArgsConstructor
public class SQLInputQueries {
    private List<String> sqlQueries;
    private SQLDialect sqlDialect;
}
