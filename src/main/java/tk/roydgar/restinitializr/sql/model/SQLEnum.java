package tk.roydgar.restinitializr.sql.model;

import lombok.Data;

import java.util.List;

@Data
public class SQLEnum {

    private String name;
    private List<String> values;

}
