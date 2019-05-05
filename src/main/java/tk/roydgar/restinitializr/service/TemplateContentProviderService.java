package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.Map;

public interface TemplateContentProviderService {

    Map<String, Object> createContextContent(SpringInitializrParameters springInitializrParameters, SQLTable sqlTable);

}
