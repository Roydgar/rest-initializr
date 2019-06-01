package tk.roydgar.restinitializr.service.impl.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.SpringInitializrProperties;
import tk.roydgar.restinitializr.model.ProjectParameters;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.service.mapper.SpringInitializrParametersMapper;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SpringInitializrParametersMapperImpl implements SpringInitializrParametersMapper {

    private final SpringInitializrProperties springInitializrProperties;

    @Override
    public SpringInitializrParameters toSpringInitializrParameters(ProjectParameters projectParameters) {
        Map<SQLDialect, String> sqlDialectToDependencyMap = springInitializrProperties.getSqlDialectToDependencyMap();
        SpringInitializrParameters initializrParameters = new SpringInitializrParameters();

        initializrParameters.setArtifactId(projectParameters.getArtifactId());
        initializrParameters.setGroupId(projectParameters.getGroupId());
        initializrParameters.setJavaVersion(projectParameters.getJavaVersion());
        initializrParameters.setType(projectParameters.getAutomationBuildSystem().getLabel());
        initializrParameters.setLanguage("java");
        initializrParameters.setBootVersion(springInitializrProperties.getSpringBootVersion());
        initializrParameters.setPackaging(projectParameters.getPackaging().getLabel());
        initializrParameters.setDescription(projectParameters.getDescription());

        List<String> dependencies = new ArrayList<>(springInitializrProperties.getDependencies());
        dependencies.add(sqlDialectToDependencyMap.get(projectParameters.getSqlDialect())); // TODO:
        initializrParameters.setDependencies(dependencies);

        return initializrParameters;
    }
}
