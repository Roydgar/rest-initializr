package tk.roydgar.restinitializr.service.impl;

import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.FileNameResolverService;
import tk.roydgar.restinitializr.service.ResourceResolverService;
import tk.roydgar.restinitializr.service.TemplateParserService;
import tk.roydgar.restinitializr.service.content.TemplatePropertiesContentProviderService;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResourceResolverServiceImpl implements ResourceResolverService {
    private final TemplateParserService templateParserService;
    private final FileNameResolverService fileNameResolverService;
    private final TemplatePropertiesContentProviderService templatePropertiesContentProviderService;
    private final List<TemplateType> resourceTemplateTypes;



    @Override
    public void resolveResourceFiles(ExtendableZipFile extendableZipFile, SpringInitializrParameters initializrParameters,
                                     PropertiesParameters propertiesParameters) throws ZipException {
        for (TemplateType templateType : resourceTemplateTypes) {
            String fileName = fileNameResolverService.resolveForResource(templateType, initializrParameters);
            Map<String, Object> contextContent = templatePropertiesContentProviderService.createContextContent(propertiesParameters);

            InputStream contentStream = templateParserService.parseTemplate(templateType, contextContent);
            extendableZipFile.removeFromZip(fileNameResolverService.resolveApplicationPropertiesPath(initializrParameters));
            extendableZipFile.addFileToZip(contentStream, fileName);
        }
    }

}
