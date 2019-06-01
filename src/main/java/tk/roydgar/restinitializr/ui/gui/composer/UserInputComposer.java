package tk.roydgar.restinitializr.ui.gui.composer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.ProjectParameters;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.SQLInputQueries;
import tk.roydgar.restinitializr.ui.gui.DataSourcePanel;
import tk.roydgar.restinitializr.ui.gui.InputPanel;
import tk.roydgar.restinitializr.ui.gui.OutputDirectoryPanel;
import tk.roydgar.restinitializr.ui.gui.SQLQueriesPanel;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserInputComposer {

    private final InputPanel userInputPanel;
    private final DataSourcePanel dataSourcePanel;
    private final SQLQueriesPanel sqlQueriesPanel;
    private final OutputDirectoryPanel outputDirectoryPanel;

    public SQLInputQueries composeSQLCreateTableQueries() {
        String[] sqlQueries = sqlQueriesPanel.getSQLQueries().split(System.lineSeparator());
        return new SQLInputQueries(Arrays.asList(sqlQueries), sqlQueriesPanel.getInputSQLDialect());
    }

    public GeneratorParameters composeUserInput() {
        PropertiesParameters propertiesParameters = new PropertiesParameters();
        ProjectParameters projectParameters = new ProjectParameters();
        GeneratorParameters generatorParameters = new GeneratorParameters();

        projectParameters.setGroupId(userInputPanel.getGroupId());
        projectParameters.setArtifactId(userInputPanel.getArtifactId());
        projectParameters.setJavaVersion(userInputPanel.getJavaVersion());
        projectParameters.setDescription(userInputPanel.getDescription());
        projectParameters.setSqlDialect(dataSourcePanel.getSQLDialect());
        projectParameters.setPackaging(userInputPanel.getPackaging());
        projectParameters.setAutomationBuildSystem(userInputPanel.getAutomationBuildSystem());

        propertiesParameters.setServerPort(userInputPanel.getServerPort());
        propertiesParameters.setDataSourceParameters(composeDataSourceParameters());

        generatorParameters.setPropertiesParameters(propertiesParameters);
        generatorParameters.setProjectParameters(projectParameters);
        generatorParameters.setOutputDirectory(outputDirectoryPanel.getOutputDirectoryPath());

        return generatorParameters;
    }

    public PropertiesParameters.DataSourceParameters composeDataSourceParameters() {
        PropertiesParameters.DataSourceParameters dataSourceParameters = new PropertiesParameters.DataSourceParameters();
        dataSourceParameters.setUrl(dataSourcePanel.getDatasourceUrl());
        dataSourceParameters.setUsername(dataSourcePanel.getDatasourceUsername());
        dataSourceParameters.setPassword(dataSourcePanel.getDatasourcePassword());
        dataSourceParameters.setDriverClassName(dataSourcePanel.getDatasourceDriverClassName());
        return dataSourceParameters;
    }

}
