package tk.roydgar.restinitializr.config.properties.sql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MySQLTypeMappingPropertiesTest {

    @Autowired
    private MySQLTypeMappingProperties properties;

    @Test
    public void propertiesLoad() {
        assertThat(properties).isNotNull();
        assertThat(properties.getSqlTypeMap()).isNotEmpty();
    }
}