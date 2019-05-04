package tk.roydgar.restinitializr.service.impl;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.JavaType;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.service.TemplateContentProviderService;
import tk.roydgar.restinitializr.sql.model.SQLColumn;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TemplateContentProviderServiceImpl implements TemplateContentProviderService {

    @Override
    public Map<TemplateKey, Object> createContextContent(SQLTable sqlTable) {
        String entityName = sqlTable.getName();
        String entityNameCapitalized = StringUtils.capitalize(sqlTable.getName());
        List<String> imports = sqlTable.getColumns()
                .stream()
                .map(SQLColumn::getJavaType)
                .map(JavaType::getImportPath)
                .collect(Collectors.toList());

        return ImmutableMap.of(
                TemplateKey.ENTITY, entityName,
                TemplateKey.ENTITY_CAPITALIZED, entityNameCapitalized,
                TemplateKey.IMPORT, imports,
                TemplateKey.SQL_TABLE, sqlTable
        );
    }

}
