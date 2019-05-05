package tk.roydgar.restinitializr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.service.TemplateContentProviderRule;
import tk.roydgar.restinitializr.service.TemplateContentProviderService;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class TemplateContentProviderServiceImpl implements TemplateContentProviderService {

    private final List<TemplateContentProviderRule> templateContentProviderRules;

    @Override
    public Map<String, Object> createContextContent(SpringInitializrParameters springInitializrParameters, SQLTable sqlTable) {
        Map<String, Object> contextContent = new HashMap<>();
        templateContentProviderRules.stream()
                .map(rule -> rule.resolveContent(springInitializrParameters, sqlTable))
                .forEach(contextContent::putAll);

        return contextContent;
    }

}
