package tk.roydgar.restinitializr.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.config.properties.FileSuffixProperties;
import tk.roydgar.restinitializr.config.properties.PackageNamingProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.ImportResolverService;
import tk.roydgar.restinitializr.util.FormatUtils;

import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class ImportResolverServiceImpl implements ImportResolverService {

    private final PackageNamingProperties packageNamingProperties;
    private final FileSuffixProperties fileSuffixProperties;

    @Override
    public String resolveImport(String entityName, TemplateType templateType, SpringInitializrParameters initializrProperties) {
        return new StringJoiner("")
                .add(resolvePackage(templateType, initializrProperties))
                .add(".")
                .add(entityName)
                .add(fileSuffixProperties.getTypeToSuffixMap().get(templateType))
                .toString();
    }

    @Override
    public String resolveImport(TemplateType templateType, SpringInitializrParameters initializrProperties) {
        return new StringJoiner("")
                .add(resolvePackage(templateType, initializrProperties))
                .add(".")
                .add(fileSuffixProperties.getTypeToSuffixMap().get(templateType))
                .toString();
    }

    @Override
    public String resolvePackage(TemplateType templateType, SpringInitializrParameters initializrProperties) {
        return new StringJoiner(".")
                .add(initializrProperties.getGroupId())
                .add(FormatUtils.formatArtifactIdToProjectPackage(initializrProperties.getArtifactId()))
                .add(packageNamingProperties.getTypeToPackageNameMap().get(templateType))
                .toString();
    }
}
