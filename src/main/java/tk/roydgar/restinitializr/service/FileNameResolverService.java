package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

public interface FileNameResolverService {

    String resolveFor(TemplateType templateType, SpringInitializrParameters initializrProperties);
    String resolveFor(String entityName, TemplateType templateType, SpringInitializrParameters initializrProperties);
    String resolveForResource(TemplateType templateType, SpringInitializrParameters initializrParameters);
    String resolveApplicationPropertiesPath(SpringInitializrParameters initializrParameters);

}
