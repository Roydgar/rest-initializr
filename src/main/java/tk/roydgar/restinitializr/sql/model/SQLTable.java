package tk.roydgar.restinitializr.sql.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SQLTable {

    private String name;
    private List<SQLColumn> columns = new ArrayList<>();
    private List<SQLEnum> enums = new ArrayList<>();

}
