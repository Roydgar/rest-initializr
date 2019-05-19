package tk.roydgar.restinitializr.service.rule;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;

import java.util.Map;

public interface TemplateStaticContentProviderRule {

    Map<String, Object> resolveContent(SpringInitializrParameters springInitializrParameters);

}
