package tk.roydgar.restinitializr.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tk.roydgar.restinitializr.config.properties.FileExtensionProperties;
import tk.roydgar.restinitializr.config.properties.FileNamingDependencyProperties;
import tk.roydgar.restinitializr.config.properties.sql.MySQLTypeMappingProperties;
import tk.roydgar.restinitializr.config.properties.sql.SQLTypeMappingProperties;
import tk.roydgar.restinitializr.model.enums.AutomationBuildSystem;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.dependency.AutomationBuildFileDependencyExtender;
import tk.roydgar.restinitializr.service.goal.AutomationBuildGoalExecutor;
import tk.roydgar.restinitializr.service.impl.dependency.GradleFileDependencyExtender;
import tk.roydgar.restinitializr.service.impl.dependency.MavenFileDependencyExtender;
import tk.roydgar.restinitializr.service.impl.goal.GradleGoalExecutor;
import tk.roydgar.restinitializr.service.impl.goal.MavenGoalExecutor;
import tk.roydgar.restinitializr.service.impl.modifier.FormattedColumnParametersSQLTableModifier;
import tk.roydgar.restinitializr.service.impl.modifier.FormattedValidationParametersSQLTableModifier;
import tk.roydgar.restinitializr.service.impl.rule.*;
import tk.roydgar.restinitializr.service.modifier.SQLTableModifier;
import tk.roydgar.restinitializr.service.rule.TemplateContentProviderRule;
import tk.roydgar.restinitializr.service.rule.TemplateStaticContentProviderRule;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.sql.visitor.SQLTableVisitor;
import tk.roydgar.restinitializr.sql.visitor.impl.SQLColumnDefinitionVisitor;
import tk.roydgar.restinitializr.sql.visitor.impl.SQLCreateTableVisitor;
import tk.roydgar.restinitializr.sql.visitor.impl.mysql.MySQLTableVisitor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tk.roydgar.restinitializr.model.enums.AutomationBuildSystem.GRADLE;
import static tk.roydgar.restinitializr.model.enums.AutomationBuildSystem.MAVEN;

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        velocityEngine.init();
        return velocityEngine;
    }

    @Bean
    public Runtime Runtime() {
        return Runtime.getRuntime();
    }

    @Bean
    public List<SQLTableVisitor> queryProcessingVisitors(SQLColumnDefinitionVisitor sqlColumnDefinitionVisitor,
                                                         SQLCreateTableVisitor sqlCreateTableVisitor,
                                                         MySQLTableVisitor mySQLTableVisitor) {
        return ImmutableList.of(sqlColumnDefinitionVisitor, sqlCreateTableVisitor, mySQLTableVisitor);
    }

    @Bean
    public Map<SQLDialect, SQLTypeMappingProperties> dialectToMappingPropertiesMap(
            MySQLTypeMappingProperties mySQLTypeMappingProperties) {
        return ImmutableMap.of(SQLDialect.MY_SQL, mySQLTypeMappingProperties);
    }

    @Bean
    public List<TemplateContentProviderRule> templateContentProviderRules(
            EntityTemplateContentProviderRule entityTemplateContentProviderRule,
            ImportTemplateContentProviderRule importTemplateContentProviderRule,
            PackageTemplateContentProviderRule packageTemplateContentProviderRule,
            SQLTableTemplateContentProviderRule sqlTableTemplateContentProviderRule,
            ClassNameTemplateContentProviderRule classNameTemplateContentProviderRule) {
        return ImmutableList.of(
                entityTemplateContentProviderRule,
                importTemplateContentProviderRule,
                packageTemplateContentProviderRule,
                sqlTableTemplateContentProviderRule,
                classNameTemplateContentProviderRule
        );
    }

    @Bean
    public List<TemplateStaticContentProviderRule> templateStaticContentProviderRules(
            ImportTemplateStaticContentProviderRule importTemplateContentProviderRule,
            PackageTemplateStaticContentProviderRule packageTemplateContentProviderRule,
            ClassNameTemplateStaticContentProviderRule classNamesTemplateContentProviderRule) {
        return ImmutableList.of(
                importTemplateContentProviderRule,
                packageTemplateContentProviderRule,
                classNamesTemplateContentProviderRule
        );
    }

    @Bean
    public List<TemplateType> staticTemplateTypes(FileNamingDependencyProperties dependencyProperties, FileExtensionProperties fileExtensionProperties) {
        Map<TemplateType, String> typeToFileExtensionMap = fileExtensionProperties.getTypeToFileExtensionMap();

        return dependencyProperties.getTemplateTypeTemplateKeyMap().entrySet()
                .stream()
                .filter(entry -> typeToFileExtensionMap.get(entry.getKey()).equals("java"))
                .filter(entry -> entry.getKey() != TemplateType.ENUM)
                .filter(entry -> entry.getValue() == TemplateKey.NONE)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Bean
    public List<TemplateType> dynamicTemplateTypes(FileNamingDependencyProperties dependencyProperties, FileExtensionProperties fileExtensionProperties) {
        Map<TemplateType, String> typeToFileExtensionMap = fileExtensionProperties.getTypeToFileExtensionMap();

        return dependencyProperties.getTemplateTypeTemplateKeyMap().entrySet()
                .stream()
                .filter(entry -> typeToFileExtensionMap.get(entry.getKey()).equals("java"))
                .filter(entry -> entry.getKey() != TemplateType.ENUM)
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> entry.getValue() != TemplateKey.NONE)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Bean
    public List<TemplateType> resourceTemplateTypes(FileExtensionProperties fileExtensionProperties) {
        Map<TemplateType, String> typeToFileExtensionMap = fileExtensionProperties.getTypeToFileExtensionMap();

        return Arrays.stream(TemplateType.values())
                .filter(templateType -> typeToFileExtensionMap.get(templateType).equals("yml") ||
                    typeToFileExtensionMap.get(templateType).equals("properties"))
                .collect(Collectors.toList());
    }

    @Bean
    public Map<AutomationBuildSystem, AutomationBuildFileDependencyExtender> projectTypeToDependencyExtenderService (
            MavenFileDependencyExtender mavenFileDependencyExtenderService,
            GradleFileDependencyExtender gradleFileDependencyExtenderService) {

        return ImmutableMap.of(
                MAVEN, mavenFileDependencyExtenderService,
                GRADLE, gradleFileDependencyExtenderService);
    }

    @Bean
    public List<SQLTableModifier> sqlTableModifiers(
            FormattedValidationParametersSQLTableModifier validationParametersSQLTableModifier,
            FormattedColumnParametersSQLTableModifier formattedColumnParametersSQLTableModifier) {
        return ImmutableList.of(
                validationParametersSQLTableModifier,
                formattedColumnParametersSQLTableModifier
        );
    }

    @Bean
    public Map<AutomationBuildSystem, AutomationBuildGoalExecutor> automationBuildGoalExecutorMap (
            MavenGoalExecutor mavenGoalExecutor, GradleGoalExecutor gradleGoalExecutor) {
        return ImmutableMap.of(
                MAVEN, mavenGoalExecutor,
                GRADLE, gradleGoalExecutor
        );
    }


}
