package tk.roydgar.restinitializr.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import tk.roydgar.restinitializr.config.properties.FileExtensionProperties;
import tk.roydgar.restinitializr.config.properties.FileSuffixProperties;
import tk.roydgar.restinitializr.config.properties.PackageNamingProperties;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RequiredPropertiesConfig {

    private final static List<TemplateKey> REQUIRED_TEMPLATE_KEY_RESOLVERS;
    private final static List<TemplateType> REQUIRED_TEMPLATE_TYPE_RESOLVERS;

    static {
        ArrayList<TemplateType> templateTypes = new ArrayList<>(Arrays.asList(TemplateType.values()));

        List<TemplateKey> templateKeys = new ArrayList<>(Arrays.asList(TemplateKey.values()));
        templateKeys.remove(TemplateKey.NONE);
        REQUIRED_TEMPLATE_KEY_RESOLVERS = templateKeys;

        REQUIRED_TEMPLATE_TYPE_RESOLVERS = templateTypes;
    }

    private final TemplateProperties templateProperties;
    private final FileExtensionProperties fileExtensionProperties;
    private final FileSuffixProperties fileSuffixProperties;
    private final PackageNamingProperties packageNamingProperties;

    @EventListener(ApplicationReadyEvent.class)
    public void checkRequiredPropertiesArePresent() {
        for (TemplateKey templateKey: REQUIRED_TEMPLATE_KEY_RESOLVERS) {
            if(templateProperties.getTemplateKeyToNameMap().get(templateKey) == null) {
                throw new IllegalStateException(String.format(
                        "%s key resolver must be present in template.properties " +
                        "like 'templateKeyToNameMap.%s = myValue'", templateKey, templateKey));
            }
        }

        for (TemplateType templateType: REQUIRED_TEMPLATE_TYPE_RESOLVERS) {
            if (fileExtensionProperties.getTypeToFileExtensionMap().get(templateType) == null) {
                throw new IllegalStateException(String.format(
                        "%s template type must be present in file-extension.yml " +
                                "like 'typeToFileExtensionMap.%s = myValue'", templateType, templateType));
            }
            if (fileSuffixProperties.getTypeToSuffixMap().get(templateType) == null) {
                throw new IllegalStateException(String.format(
                        "%s template type must be present in file-suffix.properties " +
                                "like 'typeToSuffixMap.%s = myValue'", templateType, templateType));
            }
            if (packageNamingProperties.getTypeToPackageNameMap().get(templateType) == null) {
                throw new IllegalStateException(String.format(
                        "%s template type must be present in package-naming.properties " +
                                "like 'typeToPackageNameMap.%s = myValue'", templateType, templateType));
            }
        }
    }

}
