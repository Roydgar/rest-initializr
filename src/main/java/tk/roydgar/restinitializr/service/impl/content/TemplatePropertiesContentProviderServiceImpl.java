package tk.roydgar.restinitializr.service.impl.content;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.service.content.TemplatePropertiesContentProviderService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplatePropertiesContentProviderServiceImpl implements TemplatePropertiesContentProviderService {

    private final TemplateProperties templateProperties;

    @Override
    public Map<String, Object> createContextContent(PropertiesParameters propertiesParameters) {
        Map<TemplateKey, String> templateKeyToNameMap = templateProperties.getTemplateKeyToNameMap();

        return ImmutableMap.of(
                templateKeyToNameMap.get(TemplateKey.DATASOURCE_URL), propertiesParameters.getDataSourceUrl(),
                templateKeyToNameMap.get(TemplateKey.DATASOURCE_USERNAME), propertiesParameters.getDataSourceUsername(),
                templateKeyToNameMap.get(TemplateKey.DATASOURCE_PASSWORD), propertiesParameters.getDataSourcePassword(),
                templateKeyToNameMap.get(TemplateKey.DATASOURCE_DRIVER_CLASSNAME), propertiesParameters.getDataSourceDriverClassName(),
                templateKeyToNameMap.get(TemplateKey.SERVER_PORT), propertiesParameters.getServerPort()
        );
    }
}
