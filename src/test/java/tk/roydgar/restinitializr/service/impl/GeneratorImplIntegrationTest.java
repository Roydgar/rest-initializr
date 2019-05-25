package tk.roydgar.restinitializr.service.impl;


import com.google.common.collect.ImmutableList;
import net.lingala.zip4j.exception.ZipException;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.ProjectParameters;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.enums.AutomationBuildSystem;
import tk.roydgar.restinitializr.model.enums.Packaging;
import tk.roydgar.restinitializr.model.enums.ValidationAnnotation;
import tk.roydgar.restinitializr.model.validation.ValidationEntity;
import tk.roydgar.restinitializr.model.validation.ValidationParameterEntity;
import tk.roydgar.restinitializr.service.Generator;
import tk.roydgar.restinitializr.service.impl.goal.GradleGoalExecutor;
import tk.roydgar.restinitializr.sql.model.SQLColumn;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GeneratorImplIntegrationTest {

    @Autowired
    private Generator generator;
    @Autowired
    private GradleGoalExecutor gradleGoalExecutor;
    @Test
    public void name() throws ZipException, IOException, MavenInvocationException {
        String query = "CREATE TABLE user_loh (id int auto_increment primary key, name VARCHAR(25) unique default \"loh\", " +
                "event_day ENUM ('Mon','Tue','Wed','Thu','Fri','Sat','Sun'))";
        List<String> dependencies = new ArrayList<>();
        dependencies.add("data-jpa");
        dependencies.add("mysql");
        dependencies.add("web");
        dependencies.add("lombok");

        PropertiesParameters propertiesParameters = new PropertiesParameters();
        PropertiesParameters.DataSourceParameters dataSourceParameters = new PropertiesParameters.DataSourceParameters();
        dataSourceParameters.setDialect("mysql");
        dataSourceParameters.setHost("localhost");
        dataSourceParameters.setPort("3306");
        dataSourceParameters.setDbName("odb");
        dataSourceParameters.setUseUnicode(true);
        dataSourceParameters.setServerTimeZone("UTC");
        dataSourceParameters.setCreateTableIfNotExist(true);
        dataSourceParameters.setUsername("root");
        dataSourceParameters.setPassword("root");
        dataSourceParameters.setSqlDialect(SQLDialect.MY_SQL);

        propertiesParameters.setServerPort("8080");
        propertiesParameters.setDataSourceParameters(dataSourceParameters);

        ProjectParameters projectParameters = new ProjectParameters();
        projectParameters.setArtifactId("test");
        projectParameters.setGroupId("tk.roydgar");
        projectParameters.setJavaVersion("1.8");
        projectParameters.setPackaging(Packaging.JAR);
        projectParameters.setLanguage("java");
        projectParameters.setDescription("test");
        projectParameters.setSqlDialect(SQLDialect.MY_SQL);
        projectParameters.setAutomationBuildSystem(AutomationBuildSystem.MAVEN);

        GeneratorParameters generatorParameters = new GeneratorParameters();
        generatorParameters.setSqlQueries(ImmutableList.of(query));
        generatorParameters.setProjectParameters(projectParameters);
        generatorParameters.setPropertiesParameters(propertiesParameters);

        List<SQLTable> sqlTables = generator.parseQueries(generatorParameters);
        SQLTable sqlTable = sqlTables.get(0);
        SQLColumn sqlColumn = sqlTable.getColumns().get(1);
        ValidationEntity validationEntity = new ValidationEntity();
        validationEntity.setValidationAnnotation(ValidationAnnotation.SIZE);

        ValidationParameterEntity validationParameterEntity = new ValidationParameterEntity();
        validationParameterEntity.setParameter(ValidationAnnotation.SIZE.getParameters().get(0));
        validationParameterEntity.setValue("5");
        validationEntity.setValidationParameterEntities(ImmutableList.of(validationParameterEntity));

        sqlColumn.setValidationEntities(ImmutableList.of(validationEntity));
        ExtendableZipFile zipFile = generator.generate(generatorParameters, sqlTables);

        String location = "C:\\Users\\Roydgar\\Desktop\\test";
        zipFile.unzipTo(location);

    }
}