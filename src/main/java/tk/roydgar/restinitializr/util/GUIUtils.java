package tk.roydgar.restinitializr.util;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.util.Enumeration;

@UtilityClass
public class GUIUtils {

    public static String getRequiredSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText().toUpperCase();
            }
        }
        throw new IllegalStateException("All radio button groups are required");
    }

}
