package tk.roydgar.restinitializr.config.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidationAnnotationPropertiesTest {

    @Autowired
    private ValidationAnnotationProperties properties;

    @Test
    public void propertyLoad() {
        assertThat(properties).isNotNull();
        assertThat(properties.getAnnotations()).isNotEmpty();
    }
}