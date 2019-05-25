package tk.roydgar.restinitializr.service.impl.rule;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.service.rule.TemplateContentProviderRule;
import tk.roydgar.restinitializr.sql.model.SQLColumn;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EntityTemplateContentProviderRule implements TemplateContentProviderRule {

    private final TemplateProperties templateProperties;

    @Override
    public Map<String, Object> resolveContent(SpringInitializrParameters springInitializrParameters, SQLTable sqlTable) {
        Map<TemplateKey,String> templateKeyToNameMap = templateProperties.getTemplateKeyToNameMap();

        String entityName = sqlTable.getName();
        String entityNameCapitalized = StringUtils.capitalize(sqlTable.getName());

        SQLColumn primaryKeyColumn = sqlTable.getColumns()
                .stream()
                .filter(SQLColumn::isPrimaryKey)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Several id's are not allowed"));

        return ImmutableMap.of(
                templateKeyToNameMap.get(TemplateKey.ENTITY), entityName,
                templateKeyToNameMap.get(TemplateKey.ENTITY_CAPITALIZED), entityNameCapitalized,
                templateKeyToNameMap.get(TemplateKey.ENTITY_ID_TYPE), primaryKeyColumn.getJavaTypeDefinition().getLabel()
        );
    }

}
