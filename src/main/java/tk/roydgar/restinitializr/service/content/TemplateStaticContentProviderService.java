package tk.roydgar.restinitializr.service.content;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;

import java.util.Map;

public interface TemplateStaticContentProviderService {

    Map<String, Object> createContextContent(SpringInitializrParameters springInitializrParameters);

}
