package tk.roydgar.restinitializr.service.impl.rule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.impl.rule.helper.PackageTemplateContentProviderHelper;
import tk.roydgar.restinitializr.service.rule.TemplateStaticContentProviderRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PackageTemplateStaticContentProviderRule implements TemplateStaticContentProviderRule {

    private final PackageTemplateContentProviderHelper packageTemplateContentProviderHelper;
    private final List<TemplateType> staticTemplateTypes;

    @Override
    public Map<String, Object> resolveContent(SpringInitializrParameters springInitializrParameters) {
        Map<String, Object> contextContent = new HashMap<>();
        staticTemplateTypes.stream()
                .map(templateType -> packageTemplateContentProviderHelper.resolveContent(templateType, springInitializrParameters))
                .forEach(contextContent::putAll);

        return contextContent;
    }
}
