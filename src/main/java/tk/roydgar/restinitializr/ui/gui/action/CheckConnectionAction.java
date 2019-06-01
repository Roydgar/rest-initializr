package tk.roydgar.restinitializr.ui.gui.action;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.PropertiesParameters;
import tk.roydgar.restinitializr.service.connection.ConnectionVerifier;
import tk.roydgar.restinitializr.ui.gui.Session;
import tk.roydgar.restinitializr.ui.gui.composer.UserInputComposer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class CheckConnectionAction implements ActionListener {

    private final ConnectionVerifier connectionVerifier;
    private final UserInputComposer userInputComposer;
    private final Session session;
    @Setter
    private Predicate<Session> executionCondition;


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!executionCondition.test(session)) {
            return;
        }
        PropertiesParameters.DataSourceParameters dataSourceParameters = userInputComposer.composeDataSourceParameters();
        try {
            connectionVerifier.verifyConnection(dataSourceParameters);
            JOptionPane.showMessageDialog(null, "Connection successful", "Success"
                    , JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Connection failed: " + exception.toString(), "Fail"
                    , JOptionPane.ERROR_MESSAGE);
        }

    }
}
