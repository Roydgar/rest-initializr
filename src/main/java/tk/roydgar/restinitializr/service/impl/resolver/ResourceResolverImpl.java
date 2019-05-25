package tk.roydgar.restinitializr.service.impl.resolver;

import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.content.TemplatePropertiesContentProvider;
import tk.roydgar.restinitializr.service.parser.TemplateParser;
import tk.roydgar.restinitializr.service.resolver.FileNameResolver;
import tk.roydgar.restinitializr.service.resolver.ResourceResolver;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResourceResolverImpl implements ResourceResolver {

    private final TemplateParser templateParser;
    private final FileNameResolver fileNameResolver;
    private final TemplatePropertiesContentProvider templatePropertiesContentProvider;
    private final List<TemplateType> resourceTemplateTypes;

    @Override
    public void resolveResourceFiles(ExtendableZipFile extendableZipFile, SpringInitializrParameters initializrParameters,
                                     PropertiesParameters propertiesParameters) throws ZipException {
        for (TemplateType templateType : resourceTemplateTypes) {
            String fileName = fileNameResolver.resolveForResource(templateType, initializrParameters);
            Map<String, Object> contextContent = templatePropertiesContentProvider
                    .createContextContent(propertiesParameters);

            InputStream contentStream = templateParser.parseTemplate(templateType, contextContent);
            extendableZipFile.removeFromZip(fileNameResolver.resolveApplicationPropertiesPath(initializrParameters));
            extendableZipFile.addFileToZip(contentStream, fileName);
        }
    }

}
