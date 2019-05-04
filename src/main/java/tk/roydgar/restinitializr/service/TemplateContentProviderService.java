package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.Map;

public interface TemplateContentProviderService {

    Map<TemplateKey, Object> createContextContent(SQLTable sqlTable);
}
