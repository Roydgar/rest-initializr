package tk.roydgar.restinitializr.service.impl.rule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.config.properties.TemplateTypeLabelsProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.resolver.ImportResolver;
import tk.roydgar.restinitializr.service.rule.TemplateStaticContentProviderRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImportTemplateStaticContentProviderRule implements TemplateStaticContentProviderRule {

    private final ImportResolver importResolver;
    private final TemplateProperties templateProperties;
    private final TemplateTypeLabelsProperties templateTypeLabelsProperties;
    private final List<TemplateType> staticTemplateTypes;

    @Override
    public Map<String, Object> resolveContent(SpringInitializrParameters springInitializrParameters) {
        Map<TemplateKey, String> templateKeyToNameMap = templateProperties.getTemplateKeyToNameMap();

        String importKeyName = templateKeyToNameMap.get(TemplateKey.IMPORT);
        String importFormat = importKeyName.replaceAll(templateProperties.getTemplateTypeDynamicPlaceholder(), "%s");

        Map<String, Object> contextContent = new HashMap<>();

        for (TemplateType templateType : staticTemplateTypes) {
            String importPath = importResolver.resolveImport(templateType, springInitializrParameters);
            contextContent.put(String.format(importFormat,
                            templateTypeLabelsProperties.getTemplateTypeToLabelMap().get(templateType)), importPath);
        }

        return contextContent;
    }
}
