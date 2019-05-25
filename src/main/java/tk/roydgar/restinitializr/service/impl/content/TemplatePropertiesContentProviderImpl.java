package tk.roydgar.restinitializr.service.impl.content;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.service.content.TemplatePropertiesContentProvider;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplatePropertiesContentProviderImpl implements TemplatePropertiesContentProvider {

    private final TemplateProperties templateProperties;

    @Override
    public Map<String, Object> createContextContent(PropertiesParameters propertiesParameters,  SQLDialect sqlDialect) {
        Map<TemplateKey, String> templateKeyToNameMap = templateProperties.getTemplateKeyToNameMap();
        PropertiesParameters.DataSourceParameters dataSourceParameters = propertiesParameters.getDataSourceParameters();
        Map<String, Object> contextContent = new HashMap<>();

        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_DIALECT), dataSourceParameters.getDialect());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_HOST), dataSourceParameters.getHost());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_PORT), dataSourceParameters.getPort());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_DB_NAME), dataSourceParameters.getDbName());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_USE_UNICODE), dataSourceParameters.isUseUnicode());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_SERVER_TIMEZONE), dataSourceParameters.getServerTimeZone());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_CREATE_TABLE_IF_NOT_EXIST),
                dataSourceParameters.isCreateTableIfNotExist());

        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_USERNAME), dataSourceParameters.getUsername());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_PASSWORD), dataSourceParameters.getPassword());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_DRIVER), sqlDialect.getDriverName());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.SERVER_PORT), propertiesParameters.getServerPort());

        return contextContent;
    }
}
