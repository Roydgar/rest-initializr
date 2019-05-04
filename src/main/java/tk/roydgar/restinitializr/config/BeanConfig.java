package tk.roydgar.restinitializr.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tk.roydgar.restinitializr.config.properties.sql.MySQLTypeMappingProperties;
import tk.roydgar.restinitializr.config.properties.sql.SQLTypeMappingProperties;
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

}
