package tk.roydgar.restinitializr.service.content;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.Map;

public interface TemplateContentProvider {

    Map<String, Object> createContextContent(SpringInitializrParameters springInitializrParameters, SQLTable sqlTable);

}
