package tk.roydgar.restinitializr.service.impl;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.roydgar.restinitializr.config.properties.FileExtensionProperties;
import tk.roydgar.restinitializr.config.properties.FileSuffixProperties;
import tk.roydgar.restinitializr.config.properties.PackageNamingProperties;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;
import tk.roydgar.restinitializr.service.impl.resolver.FileNameResolverImpl;

import java.io.File;
import java.util.StringJoiner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FileNameResolverImplTest {

    @Autowired
    private PackageNamingProperties packageNamingProperties;
    @Autowired
    private FileSuffixProperties fileSuffixProperties;
    @Autowired
    private FileExtensionProperties fileExtensionProperties;

    private SpringInitializrParameters initializrParameters;
    private FileNameResolverImpl subject;

    @Before
    public void setUp() {
        subject = new FileNameResolverImpl(packageNamingProperties, fileSuffixProperties, fileExtensionProperties);
        initializrParameters = new SpringInitializrParameters();
        initializrParameters.setGroupId("me.test");
        initializrParameters.setArtifactId("artifact");
    }

    @Test
    public void createFor_whenTemplateTypeIsService_successful() {
        String entityName = "test";
        String expected = new StringJoiner(File.separator)
                .add(replaceCustomSeparatorsToSystem(initializrParameters.getArtifactId()))
                .add(replaceCustomSeparatorsToSystem(packageNamingProperties.getDefaultProjectPath()))
                .add(replaceCustomSeparatorsToSystem(initializrParameters.getGroupId()))
                .add(formatArtifactNameToPackageName(initializrParameters.getArtifactId()))
                .add(replaceCustomSeparatorsToSystem(packageNamingProperties.getTypeToPackageNameMap().get( TemplateType.SERVICE)))
                .add(StringUtils.capitalize(entityName)
                        + StringUtils.capitalize(fileSuffixProperties.getTypeToSuffixMap().get(TemplateType.SERVICE))
                        + "."
                        + fileExtensionProperties.getTypeToFileExtensionMap().get(TemplateType.SERVICE))
                .toString();


        String actual = subject.resolveFor(entityName, TemplateType.SERVICE, initializrParameters);

        assertThat(actual).isEqualTo(expected);
    }

    private String replaceCustomSeparatorsToSystem(String target) {
        return StringUtils.replace(target
                , packageNamingProperties.getCustomPackageSeparator()
                , File.separator);
    }

    private String formatArtifactNameToPackageName(String artifactName) {
        String nameWithSystemSeparator = replaceCustomSeparatorsToSystem(artifactName);
        return nameWithSystemSeparator.replaceAll("-", "");
    }
}