package tk.roydgar.restinitializr.service.parser;

import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import java.io.InputStream;
import java.util.Map;

public interface TemplateParser {

    InputStream parseTemplate(TemplateType templateType, Map<String, Object> contextContent);

}
