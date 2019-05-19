package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

public interface ImportResolverService {

    String resolveImport(String entityName, TemplateType templateType, SpringInitializrParameters initializrProperties);
    String resolveImport(TemplateType templateType, SpringInitializrParameters initializrProperties);
    String resolvePackage(TemplateType templateType, SpringInitializrParameters initializrProperties);

}
