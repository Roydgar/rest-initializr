package tk.roydgar.restinitializr.service.impl.resolver;

import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.content.TemplateEnumContentProvider;
import tk.roydgar.restinitializr.service.parser.TemplateParser;
import tk.roydgar.restinitializr.service.resolver.EnumResolver;
import tk.roydgar.restinitializr.service.resolver.FileNameResolver;
import tk.roydgar.restinitializr.sql.model.SQLEnum;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.io.InputStream;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class EnumResolverImpl implements EnumResolver {

    private final TemplateParser templateParser;
    private final FileNameResolver fileNameResolver;
    private final TemplateEnumContentProvider templateEnumContentProvider;

    @Override
    public void resolveEnums(SQLTable sqlTable, ExtendableZipFile extendableZipFile, SpringInitializrParameters initializrParameters) throws ZipException {
        for (SQLEnum sqlEnum: sqlTable.getEnums()) {
            String fileName = fileNameResolver.resolveFor(sqlEnum.getName(), TemplateType.ENUM, initializrParameters);
            Map<String, Object> content = templateEnumContentProvider.createContextContent(sqlEnum, initializrParameters);

            InputStream contentStream = templateParser.parseTemplate(TemplateType.ENUM, content);
            extendableZipFile.addFileToZip(contentStream, fileName);
        }
    }
}
