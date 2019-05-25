package tk.roydgar.restinitializr.service.impl.content;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.FileSuffixProperties;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.config.properties.TemplateTypeLabelsProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.content.TemplateEnumContentProvider;
import tk.roydgar.restinitializr.service.resolver.ImportResolver;
import tk.roydgar.restinitializr.sql.model.SQLEnum;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplateEnumContentProviderImpl implements TemplateEnumContentProvider {

    private final ImportResolver importResolver;
    private final FileSuffixProperties fileSuffixProperties;
    private final TemplateProperties templateProperties;
    private final TemplateTypeLabelsProperties templateTypeLabelsProperties;

    @Override
    public Map<String, Object> createContextContent(SQLEnum sqlEnum, SpringInitializrParameters initializrParameters) {
        Map<TemplateKey, String> templateKeyToNameMap = templateProperties.getTemplateKeyToNameMap();
        Map<TemplateType, String> templateTypeToLabelMap = templateTypeLabelsProperties.getTemplateTypeToLabelMap();
        Map<TemplateType, String> typeToSuffixMap = fileSuffixProperties.getTypeToSuffixMap();

        Map<String, Object> contextContent = new HashMap<>();

        String packageKeyName = templateKeyToNameMap.get(TemplateKey.PACKAGE);
        String packageFormat = packageKeyName.replaceAll(templateProperties.getTemplateTypeDynamicPlaceholder(), "%s");

        String importPath = importResolver.resolvePackage(TemplateType.ENUM, initializrParameters);
        contextContent.put(String.format(packageFormat, templateTypeToLabelMap.get(TemplateType.ENUM)), importPath);

        String classKeyName = templateKeyToNameMap.get(TemplateKey.CLASS_NAME);
        String classFormat = classKeyName.replaceAll(templateProperties.getTemplateTypeDynamicPlaceholder(), "%s");
        String suffix = typeToSuffixMap.get(TemplateType.ENUM);
        String className = sqlEnum.getName() + suffix;
        String templateTypeLabel = templateTypeToLabelMap.get(TemplateType.ENUM);

        contextContent.put(String.format(classFormat, templateTypeLabel), StringUtils.capitalize(className));
        contextContent.put(templateProperties.getTemplateKeyToNameMap().get(TemplateKey.ENUM_VALUES), sqlEnum.getValues());

        return contextContent;
    }

}
