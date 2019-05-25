package tk.roydgar.restinitializr.service.content;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.sql.model.SQLEnum;

import java.util.Map;

public interface TemplateEnumContentProvider {

    Map<String, Object> createContextContent(SQLEnum sqlEnum, SpringInitializrParameters initializrParameters);

}
