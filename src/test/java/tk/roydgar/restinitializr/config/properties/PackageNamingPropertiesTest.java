package tk.roydgar.restinitializr.config.properties;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PackageNamingPropertiesTest {

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Autowired
    private PackageNamingProperties properties;

    @Test
    public void propertiesLoads() {
        assertThat(properties).isNotNull();
        softly.assertThat(properties.getDefaultProjectPath()).isNotBlank();
        softly.assertThat(properties.getCustomPackageSeparator()).isNotBlank();
        softly.assertThat(properties.getTypeToPackageNameMap()).isNotEmpty();
    }

}