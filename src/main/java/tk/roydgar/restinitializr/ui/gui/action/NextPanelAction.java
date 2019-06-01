package tk.roydgar.restinitializr.ui.gui.action;

import lombok.AllArgsConstructor;
import lombok.Setter;
import tk.roydgar.restinitializr.ui.gui.GUIPanel;
import tk.roydgar.restinitializr.ui.gui.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Predicate;

@AllArgsConstructor
@Setter
public class NextPanelAction implements ActionListener {

    private GUIPanel currentGUIPanel;
    private GUIPanel nextGUIPanel;
    private Session session;
    private Consumer<JPanel> customAction;
    private Predicate<Session> executionCondition;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!executionCondition.test(session)) {
            return;
        }
        currentGUIPanel.setVisible(false);
        Point currentPanelLocation = currentGUIPanel.getLocation();
        nextGUIPanel.setLocation(currentPanelLocation.x, currentPanelLocation.y);
        nextGUIPanel.setVisible(true);
        customAction.accept(nextGUIPanel.getContentPane());
    }

}
