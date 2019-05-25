package tk.roydgar.restinitializr.service.impl.rule.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.config.properties.TemplateTypeLabelsProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.resolver.ImportResolver;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PackageTemplateContentProviderHelper {

    private final ImportResolver importResolver;
    private final TemplateProperties templateProperties;
    private final TemplateTypeLabelsProperties templateTypeLabelsProperties;

    public Map<String, Object> resolveContent(TemplateType templateType, SpringInitializrParameters springInitializrParameters) {
        Map<TemplateKey, String> templateKeyToNameMap = templateProperties.getTemplateKeyToNameMap();
        Map<TemplateType, String> templateTypeToLabelMap = templateTypeLabelsProperties.getTemplateTypeToLabelMap();
        Map<String, Object> contextContent = new HashMap<>();

        String packageKeyName = templateKeyToNameMap.get(TemplateKey.PACKAGE);
        String packageFormat = packageKeyName.replaceAll(templateProperties.getTemplateTypeDynamicPlaceholder(), "%s");

        String importPath = importResolver.resolvePackage(templateType, springInitializrParameters);
        contextContent.put(String.format(packageFormat, templateTypeToLabelMap.get(templateType)), importPath);

        return contextContent;
    }
}
