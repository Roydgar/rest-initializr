package tk.roydgar.restinitializr.ui.gui;

import lombok.Getter;

import javax.swing.*;

@Getter
public class UserInputPanel {
    private JPanel contentPane;
    private JRadioButton mySQLRadioButton;
    private JTextField GroupIdTextField;
    private JTextField ArtifactIdTextField;
    private JTextArea sqlQueriesTextArea;
    private JTextField JavaVersionTextField;
    private JTextField DescriptionTextField;
    private JRadioButton jarRadioButton;
    private JRadioButton warRadioButton;
    private JRadioButton gradleRadioButton;
    private JRadioButton mavenRadioButton;
    private JLabel serverPortLabel;
    private JTextField serverPortTextField;
    private JLabel Username;
    private JPasswordField DatasourcePasswordTextField;
    private JTextField DatasourceUsernameTextField;
    private JTextField DatasourceDriverClassNameTextField;
    private JTextField DatasourceUrlTextField;
    private JButton generateButton;
    private JFileChooser outputDirectoryChooser;

    private ButtonGroup inputQueriesDialectButtonGroup;
    private ButtonGroup packagingButtonGroup;
    private ButtonGroup buildSystemButtonGroup;

    public UserInputPanel() {
        mySQLRadioButton.setSelected(true);
        jarRadioButton.setSelected(true);
        mavenRadioButton.setSelected(true);

        inputQueriesDialectButtonGroup = new ButtonGroup();
        inputQueriesDialectButtonGroup.add(mySQLRadioButton);

        packagingButtonGroup = new ButtonGroup();
        packagingButtonGroup.add(jarRadioButton);
        packagingButtonGroup.add(warRadioButton);

        buildSystemButtonGroup = new ButtonGroup();
        buildSystemButtonGroup.add(gradleRadioButton);
        buildSystemButtonGroup.add(mavenRadioButton);

        generateButton.addActionListener(e -> {

        });
    }

    private void generateAction() {

    }

}
