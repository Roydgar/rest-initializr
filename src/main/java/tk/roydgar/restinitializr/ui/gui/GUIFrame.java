package tk.roydgar.restinitializr.ui.gui;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class GUIFrame extends JFrame {

    public GUIFrame() throws HeadlessException {
        UserInputPanel userInputPanel = new UserInputPanel();
        this.setTitle("App");
        this.setPreferredSize(new Dimension(800, 1200));
        this.setContentPane(userInputPanel.getContentPane());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }
}
