package tk.roydgar.restinitializr.config.properties;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplatePropertiesTest {

    @Autowired
    private TemplateProperties templateProperties;

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void propertiesLoads() {
        softly.assertThat(templateProperties.getTemplatesRelativePath()).isNotNull();
        softly.assertThat(templateProperties.getTemplateExtension()).isNotNull();
        softly.assertThat(templateProperties.getKeyResolvers()).isNotNull();
        softly.assertThat(templateProperties.getFileNameResolvers()).isNotNull();
    }

}
