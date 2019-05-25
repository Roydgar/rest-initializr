package tk.roydgar.restinitializr.service.resolver;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

public interface FileNameResolver {

    String resolveFor(TemplateType templateType, SpringInitializrParameters initializrParameters);
    String resolveFor(String entityName, TemplateType templateType, SpringInitializrParameters initializrParameters);
    String resolveForResource(TemplateType templateType, SpringInitializrParameters initializrParameters);
    String resolveApplicationPropertiesPath(SpringInitializrParameters initializrParameters);
    String resolvePath(TemplateType templateType, SpringInitializrParameters initializrParameters);

}
