package tk.roydgar.restinitializr.ui.gui;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.ProjectParameters;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.model.enums.AutomationBuildSystem;
import tk.roydgar.restinitializr.model.enums.Packaging;
import tk.roydgar.restinitializr.service.Generator;
import tk.roydgar.restinitializr.service.connection.ConnectionVerifier;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import javax.swing.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Component
@Getter
public class InputPanel {
    private JPanel contentPane;
    private JTextField groupIdTextField;
    private JTextField artifactIdTextField;
    private JTextArea sqlQueriesTextArea;
    private JTextField javaVersionTextField;
    private JTextField descriptionTextField;
    private JLabel serverPortLabel;
    private JTextField serverPortTextField;
    private JLabel Username;
    private JRadioButton jarRadioButton;
    private JRadioButton mavenRadioButton;
    private JFileChooser outputDirectoryChooser;
    private JTextField datasourceUrlTextField;
    private JTextField datasourceUsernameTextField;
    private JPasswordField datasourcePasswordTextField;
    private JTextField datasourceDriverClassNameTextField;
    private JRadioButton warRadioButton;
    private JRadioButton gradleRadioButton;
    private JButton generateButton;
    private JComboBox sqlDialectComboBox;
    private JButton checkConnectionButton;

    private ButtonGroup packagingButtonGroup;
    private ButtonGroup buildSystemButtonGroup;

    @Autowired
    private Generator generator;
    @Autowired
    private ConnectionVerifier connectionVerifier;

    public InputPanel() {
        jarRadioButton.setSelected(true);
        mavenRadioButton.setSelected(true);

        packagingButtonGroup = new ButtonGroup();
        packagingButtonGroup.add(jarRadioButton);
        packagingButtonGroup.add(warRadioButton);

        buildSystemButtonGroup = new ButtonGroup();
        buildSystemButtonGroup.add(gradleRadioButton);
        buildSystemButtonGroup.add(mavenRadioButton);

        ComboBoxModel<SQLDialect> comboBoxModel = new DefaultComboBoxModel<>(SQLDialect.values());
        sqlDialectComboBox.setModel(comboBoxModel);

        generateButton.addActionListener(e -> generateAction());
        checkConnectionButton.addActionListener(e -> checkConnectionAction());
    }

    private void checkConnectionAction() {
        PropertiesParameters.DataSourceParameters dataSourceParameters = new PropertiesParameters.DataSourceParameters();
        dataSourceParameters.setUrl(datasourceUrlTextField.getText());
        dataSourceParameters.setUsername(datasourceUsernameTextField.getText());
        dataSourceParameters.setPassword(datasourcePasswordTextField.getText());
        dataSourceParameters.setDriverClassName(datasourceDriverClassNameTextField.getText());

        try {
            connectionVerifier.verifyConnection(dataSourceParameters);
            JOptionPane.showMessageDialog(null, "Success", "Connection successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Connection failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SneakyThrows
    private void generateAction() {
        PropertiesParameters.DataSourceParameters dataSourceParameters = new PropertiesParameters.DataSourceParameters();
        PropertiesParameters propertiesParameters = new PropertiesParameters();
        ProjectParameters projectParameters = new ProjectParameters();
        GeneratorParameters generatorParameters = new GeneratorParameters();

        SQLDialect sqlDialect = SQLDialect.valueOf(sqlDialectComboBox.getSelectedItem().toString());
        Packaging packaging = Packaging.valueOf(getSelectedButtonText(packagingButtonGroup));
        AutomationBuildSystem automationBuildSystem = AutomationBuildSystem.valueOf(getSelectedButtonText(buildSystemButtonGroup));

        projectParameters.setGroupId(groupIdTextField.getText());
        projectParameters.setArtifactId(artifactIdTextField.getText());
        projectParameters.setJavaVersion(javaVersionTextField.getText());
        projectParameters.setDescription(descriptionTextField.getText());
        projectParameters.setSqlDialect(sqlDialect);
        projectParameters.setPackaging(packaging);
        projectParameters.setAutomationBuildSystem(automationBuildSystem);

        propertiesParameters.setServerPort(serverPortTextField.getText());
        dataSourceParameters.setUrl(datasourceUrlTextField.getText());
        dataSourceParameters.setUsername(datasourceUsernameTextField.getText());
        dataSourceParameters.setPassword(datasourcePasswordTextField.getText());
        dataSourceParameters.setDriverClassName(datasourceDriverClassNameTextField.getText());
        propertiesParameters.setDataSourceParameters(dataSourceParameters);

        String[] sqlQueries = sqlQueriesTextArea.getText().split(System.lineSeparator());
        generatorParameters.setSqlQueries(Arrays.asList(sqlQueries));
        generatorParameters.setPropertiesParameters(propertiesParameters);
        generatorParameters.setProjectParameters(projectParameters);

        List<SQLTable> sqlTables = generator.parseQueries(generatorParameters);
        ExtendableZipFile zipFile = generator.generate(generatorParameters, sqlTables);
        zipFile.unzipTo(outputDirectoryChooser.getCurrentDirectory().getAbsolutePath());
    }

    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText().toUpperCase();
            }
        }
        throw new IllegalStateException("All radio button groups are required");
    }
}
