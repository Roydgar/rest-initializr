package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

public interface FileNameResolverService {

    String createFor(String entityName, TemplateType templateType, SpringInitializrParameters initializrProperties);

}
