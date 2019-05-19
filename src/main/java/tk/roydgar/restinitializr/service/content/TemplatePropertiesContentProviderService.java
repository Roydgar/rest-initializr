package tk.roydgar.restinitializr.service.content;

import tk.roydgar.restinitializr.model.PropertiesParameters;

import java.util.Map;

public interface TemplatePropertiesContentProviderService {
     Map<String, Object> createContextContent(PropertiesParameters propertiesParameters);
}
