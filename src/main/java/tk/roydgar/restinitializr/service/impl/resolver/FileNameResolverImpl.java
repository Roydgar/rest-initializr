package tk.roydgar.restinitializr.service.impl.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.config.properties.FileExtensionProperties;
import tk.roydgar.restinitializr.config.properties.FileSuffixProperties;
import tk.roydgar.restinitializr.config.properties.PackageNamingProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.resolver.FileNameResolver;
import tk.roydgar.restinitializr.util.FormatUtils;

import java.io.File;
import java.util.Map;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileNameResolverImpl implements FileNameResolver {

    private final PackageNamingProperties packageNamingProperties;
    private final FileSuffixProperties fileSuffixProperties;
    private final FileExtensionProperties fileExtensionProperties;

    @Override
    public String resolveFor(String entityName, TemplateType templateType, SpringInitializrParameters initializrParameters) {
        log.debug("Resolving file name for type: {}; entity name: {}", templateType, entityName);
        String path = resolvePath(templateType, initializrParameters);

        return new StringJoiner(File.separator)
                .add(path)
                .add(createFileName(entityName, templateType))
                .toString();
    }

    @Override
    public String resolveForResource(TemplateType templateType, SpringInitializrParameters initializrParameters) {
        return new StringJoiner(File.separator)
                .add(replaceCustomSeparatorsToSystem(initializrParameters.getArtifactId()))
                .add(replaceCustomSeparatorsToSystem(packageNamingProperties.getResourcesPath()))
                .add(createFileName(templateType))
                .toString();
    }

    @Override
    public String resolveApplicationPropertiesPath(SpringInitializrParameters initializrParameters) {
        return new StringJoiner(File.separator)
                .add(replaceCustomSeparatorsToSystem(initializrParameters.getArtifactId()))
                .add(replaceCustomSeparatorsToSystem(packageNamingProperties.getResourcesPath()))
                .add("application.properties")
                .toString();
    }

    @Override
    public String resolveFor(TemplateType templateType, SpringInitializrParameters initializrParameters) {
        log.debug("Resolving file name for type: {}", templateType);
        String path = resolvePath(templateType, initializrParameters);

        return new StringJoiner(File.separator)
                .add(path)
                .add(createFileName(templateType))
                .toString();
    }

    public String resolvePath(TemplateType templateType, SpringInitializrParameters initializrParameters) {
        Map<TemplateType, String> typeToPackageNameMap = packageNamingProperties.getTypeToPackageNameMap();
        StringJoiner joiner = new StringJoiner(File.separator);

        return joiner
                .add(replaceCustomSeparatorsToSystem(initializrParameters.getArtifactId()))
                .add(replaceCustomSeparatorsToSystem(packageNamingProperties.getDefaultProjectPath()))
                .add(replaceCustomSeparatorsToSystem(initializrParameters.getGroupId()))
                .add(formatArtifactNameToPackageName(initializrParameters.getArtifactId()))
                .add(replaceCustomSeparatorsToSystem(typeToPackageNameMap.get(templateType)))
                .toString();
    }

    private String replaceCustomSeparatorsToSystem(String target) {
        return StringUtils.replace(target
                , packageNamingProperties.getCustomPackageSeparator()
                , File.separator);
    }

    private String formatArtifactNameToPackageName(String artifactName) {
        String nameWithSystemSeparator = replaceCustomSeparatorsToSystem(artifactName);
        return FormatUtils.formatArtifactIdToProjectPackage(nameWithSystemSeparator);
    }

    private String createFileName(String entityName, TemplateType templateType) {
        String formattedEntityName = StringUtils.capitalize(entityName);
        return formattedEntityName + createFileName(templateType);
    }

    private String createFileName(TemplateType templateType) {
        String suffix = fileSuffixProperties.getTypeToSuffixMap().get(templateType);
        String extension = fileExtensionProperties.getTypeToFileExtensionMap().get(templateType);

        String formattedSuffix = StringUtils.capitalize(suffix);

        return formattedSuffix + "." + extension;
    }

}
