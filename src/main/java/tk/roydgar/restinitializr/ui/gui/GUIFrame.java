package tk.roydgar.restinitializr.ui.gui;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.GUIProperties;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@Setter(onMethod = @__({@Autowired}))
public class GUIFrame extends JFrame {

    private GUIProperties guiProperties;
    private SQLQueriesPanel sqlQueriesPanel;

    @PostConstruct
    public void setUp() {
        this.setTitle(guiProperties.getWindowTitle());
        this.setPreferredSize(new Dimension(guiProperties.getWindowWidth(), guiProperties.getWindowHeight()));
        this.setContentPane(sqlQueriesPanel.getContentPane());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        sqlQueriesPanel.setVisible(true);
    }

}
