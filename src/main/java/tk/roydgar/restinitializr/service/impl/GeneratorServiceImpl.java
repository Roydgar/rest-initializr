package tk.roydgar.restinitializr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.client.SpringInitializrClient;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.*;
import tk.roydgar.restinitializr.service.content.TemplateContentProviderService;
import tk.roydgar.restinitializr.service.content.TemplateStaticContentProviderService;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final SpringInitializrClient springInitializrClient;
    private final SQLParserService sqlParserService;
    private final TemplateParserService templateParserService;
    private final TemplateContentProviderService templateContentProviderService;
    private final TemplateStaticContentProviderService templateStaticContentProviderService;
    private final FileNameResolverService fileNameResolverService;
    private final ResourceResolverService resourceResolverService;

    private final List<TemplateType> staticTemplateTypes;
    private final List<TemplateType> dynamicTemplateTypes;
    private final Map<String, AutomationBuildFileDependencyExtenderService> projectTypeToDependencyExtenderService;
    private final MavenFileDependencyExtenderService mavenFileDependencyExtenderService;

    @Value("${zip-file.tempDir}")
    private String tempDirName;

    @Override
    @SneakyThrows
    public ExtendableZipFile generate(GeneratorParameters generatorParameters) {
        List<SQLTable> sqlTables = generatorParameters.getSqlQueries()
                .stream()
                .map(query -> sqlParserService.parseCreateQuery(query, generatorParameters.getSqlDialect()))
                .collect(Collectors.toList());

        SpringInitializrParameters initializrParameters = generatorParameters.getInitializrParameters();
        byte[] springInitializrZipFile = springInitializrClient.getSpringStarterContent(initializrParameters);

        ExtendableZipFile extendableZipFile = new ExtendableZipFile(tempDirName, springInitializrZipFile);
        Map<String, Object> templateStaticContext = templateStaticContentProviderService.createContextContent(initializrParameters);

        for (TemplateType templateType : staticTemplateTypes) {
            InputStream contentStream = templateParserService.parseTemplate(templateType, templateStaticContext);
            String fileName = fileNameResolverService.resolveFor(templateType, initializrParameters);
            extendableZipFile.addFileToZip(contentStream, fileName);
        }

        for (SQLTable sqlTable: sqlTables) {
            Map<String, Object> templateContext = templateContentProviderService
                    .createContextContent(initializrParameters, sqlTable);
            templateContext.putAll(templateStaticContext);

            for (TemplateType templateType: dynamicTemplateTypes) {
                InputStream contentStream = templateParserService.parseTemplate(templateType, templateContext);
                String fileName = fileNameResolverService.resolveFor(sqlTable.getName(), templateType, initializrParameters);
                extendableZipFile.addFileToZip(contentStream, fileName);
            }
        }

        resourceResolverService.resolveResourceFiles(extendableZipFile, initializrParameters, generatorParameters.getPropertiesParameters());

        mavenFileDependencyExtenderService.addDependencies(extendableZipFile, initializrParameters.getArtifactId());
        return extendableZipFile;
    }

}
