package tk.roydgar.restinitializr.ui.gui;

import lombok.Data;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;

import java.util.List;

@Component
@Data
public class Session {
    private List<SQLTable> sqlTables;
    private ValidationResult validationResult;
}
