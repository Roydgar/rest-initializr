package tk.roydgar.restinitializr.service.impl.rule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.service.TemplateContentProviderRule;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SQLTableTemplateContentProviderRule implements TemplateContentProviderRule {

    private final TemplateProperties templateProperties;

    @Override
    public Map<String, Object> resolveContent(SpringInitializrParameters springInitializrParameters, SQLTable sqlTable) {
        Map<String, Object> contextContent = new HashMap<>();
        contextContent.put(templateProperties.getTemplateKeyToNameMap().get(TemplateKey.SQL_TABLE), sqlTable);

        return contextContent;
    }

}
