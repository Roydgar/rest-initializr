package tk.roydgar.restinitializr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.config.properties.FileExtensionProperties;
import tk.roydgar.restinitializr.config.properties.FileSuffixProperties;
import tk.roydgar.restinitializr.config.properties.PackageNamingProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.FileNameResolverService;

import java.io.File;
import java.util.Map;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
public class BestPracticedFileNameResolverService implements FileNameResolverService {

    private final PackageNamingProperties packageNamingProperties;
    private final FileSuffixProperties fileSuffixProperties;
    private final FileExtensionProperties fileExtensionProperties;

    @Override
    public String createFor(String entityName, TemplateType templateType, SpringInitializrParameters initializrParameters) {
        log.debug("Resolving file name for type: {}; entity name: {}", templateType, entityName);

        Map<TemplateType, String> typeToPackageNameMap = packageNamingProperties.getTypeToPackageNameMap();
        StringJoiner joiner = new StringJoiner(File.separator);

        return joiner
                .add(replaceCustomSeparatorsToSystem(initializrParameters.getArtifactId()))
                .add(replaceCustomSeparatorsToSystem(packageNamingProperties.getDefaultProjectPath()))
                .add(replaceCustomSeparatorsToSystem(initializrParameters.getGroupId()))
                .add(formatArtifactNameToPackageName(initializrParameters.getArtifactId()))
                .add(replaceCustomSeparatorsToSystem(typeToPackageNameMap.get(templateType)))
                .add(createFileName(entityName, templateType))
                .toString();
    }

    private String replaceCustomSeparatorsToSystem(String target) {
        return StringUtils.replace(target
                , packageNamingProperties.getCustomPackageSeparator()
                , File.separator);
    }

    private String formatArtifactNameToPackageName(String artifactName) {
        String nameWithSystemSeparator = replaceCustomSeparatorsToSystem(artifactName);
        return nameWithSystemSeparator.replaceAll("-", "");
    }

    private String createFileName(String entityName, TemplateType templateType) {
        String suffix = fileSuffixProperties.getTypeToSuffixMap().get(templateType);
        String extension = fileExtensionProperties.getTypeToFileExtensionMap().get(templateType);

        String formattedSuffix = StringUtils.capitalize(suffix.toLowerCase());
        String formattedEntityName = StringUtils.capitalize(entityName.toLowerCase());

        return formattedEntityName + formattedSuffix + "." + extension;
    }

}
