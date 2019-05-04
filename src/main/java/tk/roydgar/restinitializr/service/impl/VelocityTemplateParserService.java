package tk.roydgar.restinitializr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.exception.CannotParseTemplateException;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.TemplateParserService;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VelocityTemplateParserService implements TemplateParserService {

    private final VelocityEngine velocityEngine;
    private final TemplateProperties templateProperties;

    @Override
    @SneakyThrows
    public InputStream parseTemplate(TemplateType templateType, Map<TemplateKey, String> contextContent) {
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

    private VelocityContext prepareContext(Map<TemplateKey, String> contextContent) {
        VelocityContext context = new VelocityContext();
        Map<TemplateKey, String> propertyKeyResolvers = templateProperties.getKeyResolvers();

        contextContent.forEach((key, value) -> context.put(propertyKeyResolvers.get(key), value));
        return context;
    }

    private String resolveTemplateRelativePath(TemplateType templateType) {
        return File.separator
                + templateProperties.getTemplatesRelativePath()
                + File.separator
                + templateProperties.getFileNameResolvers().get(templateType)
                + "."
                + templateProperties.getTemplateExtension();
    }

}
