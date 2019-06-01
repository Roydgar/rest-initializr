package tk.roydgar.restinitializr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.client.SpringInitializrClient;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.ProjectParameters;
import tk.roydgar.restinitializr.model.SQLInputQueries;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.AutomationBuildSystem;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.Generator;
import tk.roydgar.restinitializr.service.content.TemplateContentProvider;
import tk.roydgar.restinitializr.service.content.TemplateStaticContentProvider;
import tk.roydgar.restinitializr.service.dependency.AutomationBuildFileDependencyExtender;
import tk.roydgar.restinitializr.service.mapper.SpringInitializrParametersMapper;
import tk.roydgar.restinitializr.service.modifier.SQLTableModifier;
import tk.roydgar.restinitializr.service.parser.TemplateParser;
import tk.roydgar.restinitializr.service.resolver.EnumResolver;
import tk.roydgar.restinitializr.service.resolver.FileNameResolver;
import tk.roydgar.restinitializr.service.resolver.ImportResolver;
import tk.roydgar.restinitializr.service.resolver.ResourceResolver;
import tk.roydgar.restinitializr.sql.model.SQLEnum;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.parser.SQLParser;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneratorImpl implements Generator {

    private final SpringInitializrClient springInitializrClient;
    private final SQLParser sqlParser;
    private final TemplateParser templateParser;
    private final TemplateContentProvider templateContentProvider;
    private final TemplateStaticContentProvider templateStaticContentProvider;
    private final FileNameResolver fileNameResolver;
    private final ImportResolver importResolver;
    private final ResourceResolver resourceResolver;
    private final SpringInitializrParametersMapper springInitializrParametersMapper;
    private final EnumResolver enumResolver;

    private final List<TemplateType> staticTemplateTypes;
    private final List<TemplateType> dynamicTemplateTypes;
    private final List<SQLTableModifier> sqlTableModifiers;
    private final Map<AutomationBuildSystem, AutomationBuildFileDependencyExtender> projectTypeToDependencyExtender;

    @Value("${zip-file.tempDir}")
    private String tempDirName;

    @Override
    public List<SQLTable> parseQueries(SQLInputQueries sqlInputQueries) {
        return sqlInputQueries.getSqlQueries()
                .stream()
                .map(query -> sqlParser.parseCreateQuery(query, sqlInputQueries.getSqlDialect()))
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public ExtendableZipFile generate(GeneratorParameters generatorParameters, List<SQLTable> sqlTables) {
        ProjectParameters projectParameters = generatorParameters.getProjectParameters();
        SpringInitializrParameters initializrParameters = springInitializrParametersMapper
                .toSpringInitializrParameters(projectParameters);

        byte[] springInitializrZipFile = springInitializrClient.getSpringStarterContent(initializrParameters);

        ExtendableZipFile extendableZipFile = new ExtendableZipFile(tempDirName, springInitializrZipFile);
        Map<String, Object> templateStaticContext = templateStaticContentProvider.createContextContent(initializrParameters);

        for (TemplateType templateType : staticTemplateTypes) {
            InputStream contentStream = templateParser.parseTemplate(templateType, templateStaticContext);
            String fileName = fileNameResolver.resolveFor(templateType, initializrParameters);
            extendableZipFile.addFileToZip(contentStream, fileName);
        }

        List<String> enumImports = new ArrayList<>();
        for (SQLTable sqlTable: sqlTables) {
            for (SQLEnum sqlEnum: sqlTable.getEnums()) {
                enumImports.add(importResolver.resolveImport(StringUtils.capitalize(sqlEnum.getName()),
                        TemplateType.ENUM, initializrParameters));
            }
        }
        for (SQLTable sqlTable: sqlTables) {
            sqlTableModifiers.forEach(sqlTableModifier -> sqlTableModifier.execute(sqlTable));

            Map<String, Object> templateContext = templateContentProvider
                    .createContextContent(initializrParameters, sqlTable);
            templateContext.put("enum-imports", enumImports);
            templateContext.putAll(templateStaticContext);

            for (TemplateType templateType: dynamicTemplateTypes) {
                InputStream contentStream = templateParser.parseTemplate(templateType, templateContext);
                String fileName = fileNameResolver.resolveFor(sqlTable.getName(), templateType, initializrParameters);
                extendableZipFile.addFileToZip(contentStream, fileName);
            }

            enumResolver.resolveEnums(sqlTable, extendableZipFile, initializrParameters);
        }

        resourceResolver.resolveResourceFiles(extendableZipFile, initializrParameters, generatorParameters.getPropertiesParameters());

        AutomationBuildFileDependencyExtender dependencyExtender = projectTypeToDependencyExtender
                .get(projectParameters.getAutomationBuildSystem());
        dependencyExtender.addDependencies(extendableZipFile, initializrParameters.getArtifactId());

        return extendableZipFile;
    }

}
