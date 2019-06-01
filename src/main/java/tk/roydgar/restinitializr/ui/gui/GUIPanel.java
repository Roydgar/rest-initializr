package tk.roydgar.restinitializr.ui.gui;

import javax.swing.*;
import java.awt.*;

public interface GUIPanel {
    JPanel getContentPane();
    void setVisible(boolean visible);
    void setLocation(int x, int y);
    Point getLocation();
    void setSize(int width, int height);
    void validate();
}
