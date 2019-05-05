package tk.roydgar.restinitializr.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tk.roydgar.restinitializr.config.properties.sql.MySQLTypeMappingProperties;
import tk.roydgar.restinitializr.config.properties.sql.SQLTypeMappingProperties;
import tk.roydgar.restinitializr.service.TemplateContentProviderRule;
import tk.roydgar.restinitializr.service.impl.rule.*;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.sql.visitor.SQLTableVisitor;
import tk.roydgar.restinitializr.sql.visitor.impl.SQLColumnDefinitionVisitor;
import tk.roydgar.restinitializr.sql.visitor.impl.SQLCreateTableVisitor;
import tk.roydgar.restinitializr.sql.visitor.impl.mysql.MySQLTableVisitor;

import java.util.List;
import java.util.Map;

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
            ClassNamesTemplateContentProviderRule classNamesTemplateContentProviderRule) {
        return ImmutableList.of(
                entityTemplateContentProviderRule,
                importTemplateContentProviderRule,
                packageTemplateContentProviderRule,
                sqlTableTemplateContentProviderRule,
                classNamesTemplateContentProviderRule
        );
    }

}
