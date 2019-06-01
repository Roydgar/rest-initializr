package tk.roydgar.restinitializr.ui.gui;

import lombok.Getter;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.util.CompositeActionListenerWithPriorities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Component
public class OutputDirectoryPanel implements GUIPanel {
    private JFileChooser outputDirectoryChooser;
    private JButton generateButton;
    @Getter
    private JPanel contentPane;
    private CompositeActionListenerWithPriorities prioritiesListener = new CompositeActionListenerWithPriorities();

    public void addGenerateButtonActionListener(ActionListener actionListener) {
        generateButton.addActionListener(actionListener);
    }

    public String getOutputDirectoryPath() {
        return outputDirectoryChooser.getCurrentDirectory().getAbsolutePath();
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
    }
}
