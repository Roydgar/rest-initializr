package tk.roydgar.restinitializr.ui.gui;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;
import tk.roydgar.restinitializr.ui.gui.validator.impl.DatasourcePanelValidator;
import tk.roydgar.restinitializr.util.CompositeActionListenerWithPriorities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Component
public class DataSourcePanel implements GUIPanel {
    @Getter
    private JPanel contentPane;
    private JButton nextButton;
    private JComboBox sqlDialectComboBox;
    private JTextField datasourceUrlTextField;
    private JPasswordField datasourcePasswordTextField;
    private JTextField datasourceUsernameTextField;
    private JTextField datasourceDriverClassNameTextField;
    private JButton checkConnectionButton;

    @Autowired
    private DatasourcePanelValidator panelValidator;
    @Autowired
    private Session session;

    private CompositeActionListenerWithPriorities prioritiesListener = new CompositeActionListenerWithPriorities();

    public DataSourcePanel() {
        ComboBoxModel<SQLDialect> comboBoxModel = new DefaultComboBoxModel<>(SQLDialect.values());
        sqlDialectComboBox.setModel(comboBoxModel);

        nextButton.addActionListener(prioritiesListener);
    }

    public void addNextButtonActionListener(ActionListener actionListener, int priority) {
        prioritiesListener.addActionListener(actionListener, priority);
    }

    public void addCheckConnectionButtonActionListener(ActionListener actionListener) {
        checkConnectionButton.addActionListener(actionListener);
    }

    public String getDatasourceUrl() {
        return datasourceUrlTextField.getText();
    }

    public String getDatasourceUsername() {
        return datasourceUsernameTextField.getText();
    }

    public String getDatasourcePassword() {
        return datasourcePasswordTextField.getText();
    }

    public String getDatasourceDriverClassName() {
        return datasourceDriverClassNameTextField.getText();
    }

    public SQLDialect getSQLDialect() {
        return SQLDialect.valueOf(sqlDialectComboBox.getSelectedItem().toString());
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
