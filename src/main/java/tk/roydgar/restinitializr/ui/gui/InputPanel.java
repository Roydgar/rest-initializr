package tk.roydgar.restinitializr.ui.gui;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.enums.AutomationBuildSystem;
import tk.roydgar.restinitializr.model.enums.Packaging;
import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;
import tk.roydgar.restinitializr.ui.gui.validator.impl.ProjectParametersPanelValidator;
import tk.roydgar.restinitializr.util.CompositeActionListenerWithPriorities;
import tk.roydgar.restinitializr.util.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Component
public class InputPanel implements GUIPanel {
    @Getter
    private JPanel contentPane;
    private JTextField groupIdTextField;
    private JTextField artifactIdTextField;
    private JTextField javaVersionTextField;
    private JTextField descriptionTextField;
    private JTextField serverPortTextField;
    private JRadioButton jarRadioButton;
    private JRadioButton mavenRadioButton;

    private JRadioButton warRadioButton;
    private JRadioButton gradleRadioButton;
    private JButton nextButton;

    private ButtonGroup packagingButtonGroup;
    private ButtonGroup buildSystemButtonGroup;

    @Autowired
    private ProjectParametersPanelValidator panelValidator;
    @Autowired
    private Session session;
    private CompositeActionListenerWithPriorities prioritiesListener = new CompositeActionListenerWithPriorities();

    public InputPanel() {
        jarRadioButton.setSelected(true);
        mavenRadioButton.setSelected(true);

        packagingButtonGroup = new ButtonGroup();
        packagingButtonGroup.add(jarRadioButton);
        packagingButtonGroup.add(warRadioButton);

        buildSystemButtonGroup = new ButtonGroup();
        buildSystemButtonGroup.add(gradleRadioButton);
        buildSystemButtonGroup.add(mavenRadioButton);

        nextButton.addActionListener(prioritiesListener);
    }

    public void addNextButtonActionListener(ActionListener actionListener, int priority) {
        prioritiesListener.addActionListener(actionListener, priority);
    }

    public String getGroupId() {
        return groupIdTextField.getText();
    }

    public String getArtifactId() {
        return artifactIdTextField.getText();
    }

    public String getJavaVersion() {
        return javaVersionTextField.getText();
    }

    public String getDescription() {
        return descriptionTextField.getText();
    }

    public String getServerPort() {
        return serverPortTextField.getText();
    }

    public Packaging getPackaging() {
        String formattedPackagingName = GUIUtils.getRequiredSelectedButtonText(packagingButtonGroup);
        return Packaging.valueOf(formattedPackagingName);
    }

    public AutomationBuildSystem getAutomationBuildSystem() {
        String formattedPackagingName = GUIUtils.getRequiredSelectedButtonText(buildSystemButtonGroup);
        return AutomationBuildSystem.valueOf(formattedPackagingName);
    }

    @Override
    public void setVisible(boolean visible) {
        contentPane.setVisible(visible);
    }

    @Override
    public void setLocation(int x, int y) {
        contentPane.setLocation(x, y);
    }

    @Override
    public Point getLocation() {
        return contentPane.getLocation();
    }

    @Override
    public void setSize(int width, int height) {
        contentPane.setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void validate() {
        ValidationResult validationResult = panelValidator.execute();
        session.setValidationResult(validationResult);
        if (!validationResult.isSuccess()) {
            JOptionPane.showMessageDialog(null, "Validation Issue: "
                    + validationResult.getMessage(), "Please, correct your input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
