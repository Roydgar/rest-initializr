package tk.roydgar.restinitializr.service.impl.parser;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.exception.CannotParseTemplateException;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.parser.TemplateParser;

import java.io.*;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VelocityTemplateParser implements TemplateParser {

    private final VelocityEngine velocityEngine;
    private final TemplateProperties templateProperties;

    @Override
    @SneakyThrows
    public InputStream parseTemplate(TemplateType templateType, Map<String, Object> contextContent) {
        String templatePath = resolveTemplateRelativePath(templateType);
        log.debug("Resolving template {}", templatePath);
        VelocityContext velocityContext = prepareContext(contextContent);
        log.trace("Put next variables to template context: {}", velocityContext.getKeys());

        Template template = Optional.ofNullable(velocityEngine.getTemplate(templatePath))
                .orElseThrow(() -> new CannotParseTemplateException("Template is null. Cannot parse or find template in path: " + templatePath));

        log.debug("Parsed template {}", templatePath);
        return mergeResultToInputStream(velocityContext, template);
    }

    private InputStream mergeResultToInputStream(VelocityContext velocityContext, Template template) throws IOException {
        try(Writer parsedTemplateWriter = new StringWriter()) {
            template.merge(velocityContext, parsedTemplateWriter);
            byte[] resultContent = parsedTemplateWriter.toString().getBytes();

            return new ByteArrayInputStream(resultContent);
        }
    }

    private VelocityContext prepareContext(Map<String, Object> contextContent) {
        VelocityContext context = new VelocityContext();

        contextContent.forEach(context::put);
        return context;
    }

    private String resolveTemplateRelativePath(TemplateType templateType) {
        return  templateProperties.getTemplatesRelativePath()
                + File.separator
                + templateProperties.getTemplateTypeToTemplateFileNameMap().get(templateType)
                + "."
                + templateProperties.getTemplateExtension();
    }

}
