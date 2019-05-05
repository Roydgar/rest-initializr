package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.Map;

public interface TemplateContentProviderRule {
    Map<String, Object> resolveContent(SpringInitializrParameters springInitializrParameters, SQLTable sqlTable);
}
