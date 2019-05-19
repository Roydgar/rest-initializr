package tk.roydgar.restinitializr.service.impl.content;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.service.content.TemplateStaticContentProviderService;
import tk.roydgar.restinitializr.service.rule.TemplateStaticContentProviderRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateStaticContentProviderServiceImpl implements TemplateStaticContentProviderService {

    private final List<TemplateStaticContentProviderRule> templateStaticContentProviderRules;
    @Override
    public Map<String, Object> createContextContent(SpringInitializrParameters springInitializrParameters) {
        Map<String, Object> contextContent = new HashMap<>();
        templateStaticContentProviderRules.stream()
                .map(rule -> rule.resolveContent(springInitializrParameters))
                .forEach(contextContent::putAll);


        return contextContent;
    }

}
