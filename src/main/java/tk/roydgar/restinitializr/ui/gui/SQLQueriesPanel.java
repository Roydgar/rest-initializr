package tk.roydgar.restinitializr.ui.gui;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;
import tk.roydgar.restinitializr.ui.gui.validator.impl.SQLQueriesPanelValidator;
import tk.roydgar.restinitializr.util.CompositeActionListenerWithPriorities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Component
public class SQLQueriesPanel implements GUIPanel {
    @Getter
    private JPanel contentPane;
    private JTextArea sqlQueriesTextArea;
    private JComboBox sqlDialectComboBox;
    private JButton nextButton;
    private JScrollPane queriesTextAreaScrollPane;
    private CompositeActionListenerWithPriorities prioritiesListener = new CompositeActionListenerWithPriorities();

    @Autowired
    private SQLQueriesPanelValidator panelValidator;
    @Autowired
    private Session session;

    public SQLQueriesPanel() {
        queriesTextAreaScrollPane.setViewportView(sqlQueriesTextArea);
        ComboBoxModel<SQLDialect> comboBoxModel = new DefaultComboBoxModel<>(SQLDialect.values());
        sqlDialectComboBox.setModel(comboBoxModel);

        nextButton.addActionListener(prioritiesListener);
    }

    public void addNextButtonActionListener(ActionListener actionListener, int priority) {
        prioritiesListener.addActionListener(actionListener, priority);
    }

    public SQLDialect getInputSQLDialect() {
        return SQLDialect.valueOf(sqlDialectComboBox.getSelectedItem().toString());
    }

    public String getSQLQueries() {
        return sqlQueriesTextArea.getText();
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
