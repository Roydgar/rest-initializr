package tk.roydgar.restinitializr.ui.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.GUIProperties;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class GUIFrame extends JFrame {

    @Autowired
    private GUIProperties guiProperties;
    @Autowired
    private InputPanel userInputPanel;

    @PostConstruct
    public void dod() {
        this.setTitle(guiProperties.getWindowTitle());
        this.setPreferredSize(new Dimension(guiProperties.getWindowWidth(), guiProperties.getWindowHeight()));
        this.setContentPane(userInputPanel.getContentPane());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }

}
