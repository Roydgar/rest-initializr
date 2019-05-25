package tk.roydgar.restinitializr.service.impl.content;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.service.content.TemplatePropertiesContentProvider;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplatePropertiesContentProviderImpl implements TemplatePropertiesContentProvider {

    private final TemplateProperties templateProperties;

    @Override
    public Map<String, Object> createContextContent(PropertiesParameters propertiesParameters) {
        Map<TemplateKey, String> templateKeyToNameMap = templateProperties.getTemplateKeyToNameMap();
        PropertiesParameters.DataSourceParameters dataSourceParameters = propertiesParameters.getDataSourceParameters();
        Map<String, Object> contextContent = new HashMap<>();

        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_URL), dataSourceParameters.getUrl());


        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_USERNAME), dataSourceParameters.getUsername());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_PASSWORD), dataSourceParameters.getPassword());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.DATASOURCE_DRIVER), dataSourceParameters.getDriverClassName());
        contextContent.put(templateKeyToNameMap.get(TemplateKey.SERVER_PORT), propertiesParameters.getServerPort());

        return contextContent;
    }
}
