package tk.roydgar.restinitializr.service.impl.rule;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.FileSuffixProperties;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.config.properties.TemplateTypeLabelsProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.TemplateContentProviderRule;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ClassNamesTemplateContentProviderRule implements TemplateContentProviderRule {

    private final FileSuffixProperties fileSuffixProperties;
    private final TemplateProperties templateProperties;
    private final TemplateTypeLabelsProperties templateTypeLabelsProperties;

    @Override
    public Map<String, Object> resolveContent(SpringInitializrParameters springInitializrParameters, SQLTable sqlTable) {
        Map<TemplateKey, String> templateKeyToNameMap = templateProperties.getTemplateKeyToNameMap();
        Map<TemplateType, String> templateTypeToLabelMap = templateTypeLabelsProperties.getTemplateTypeToLabelMap();
        Map<TemplateType, String> typeToSuffixMap = fileSuffixProperties.getTypeToSuffixMap();

        String entityNameCapitalized = StringUtils.capitalize(sqlTable.getName());

        String importKeyName = templateKeyToNameMap.get(TemplateKey.CLASS_NAME);
        String importFormat = importKeyName.replaceAll(templateProperties.getTemplateTypeDynamicPlaceholder(), "%s");

        Map<String, Object> contextContent = new HashMap<>();

        for (TemplateType templateType : TemplateType.values()) {
            String suffix = typeToSuffixMap.get(templateType);
            String className = entityNameCapitalized + suffix;
            String templateTypeLabel = templateTypeToLabelMap.get(templateType);

            contextContent.put(String.format(importFormat, templateTypeLabel), className);
        }

        return contextContent;
    }
}
