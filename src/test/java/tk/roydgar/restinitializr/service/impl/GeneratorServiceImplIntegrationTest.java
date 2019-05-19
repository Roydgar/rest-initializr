package tk.roydgar.restinitializr.service.impl;


import com.google.common.collect.ImmutableList;
import net.lingala.zip4j.exception.ZipException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.service.GeneratorService;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GeneratorServiceImplIntegrationTest {

    @Autowired
    private GeneratorService generatorService;

    @Test
    public void name() throws ZipException, IOException {
        String query = "CREATE TABLE user (id int auto_increment primary key, name VARCHAR)";
        List<String> dependencies = new ArrayList<>();
        dependencies.add("data-jpa");
        dependencies.add("mysql");
        dependencies.add("web");
        dependencies.add("lombok");

        PropertiesParameters propertiesParameters = new PropertiesParameters();
        propertiesParameters.setDataSourceUrl("jdbc:mysql://localhost:3306/odb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        propertiesParameters.setDataSourceUsername("root");
        propertiesParameters.setDataSourcePassword("root");
        propertiesParameters.setDataSourceDriverClassName("com.mysql.jdbc.Driver");
        propertiesParameters.setServerPort("8080");

        SpringInitializrParameters initializrParameters = new SpringInitializrParameters();
        initializrParameters.setArtifactId("test");
        initializrParameters.setGroupId("tk.roydgar");
        initializrParameters.setJavaVersion("1.8");
        initializrParameters.setType("maven-project");
        initializrParameters.setLanguage("java");
        initializrParameters.setBootVersion("2.1.5.RELEASE");
        initializrParameters.setDescription("test");
        initializrParameters.setPackaging("jar");
        initializrParameters.setDependencies(dependencies);

        GeneratorParameters generatorParameters = new GeneratorParameters();
        generatorParameters.setSqlDialect(SQLDialect.MY_SQL);
        generatorParameters.setSqlQueries(ImmutableList.of(query));
        generatorParameters.setInitializrParameters(initializrParameters);
        generatorParameters.setPropertiesParameters(propertiesParameters);

        ExtendableZipFile zipFile = generatorService.generate(generatorParameters);

        zipFile.unzipTo("C:\\Users\\Roydgar\\Desktop\\test");

        zipFile.close();
    }
}