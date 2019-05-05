package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import java.io.InputStream;
import java.util.Map;

public interface TemplateParserService {

    InputStream parseTemplate(TemplateType templateType, Map<String, Object> contextContent);

}
