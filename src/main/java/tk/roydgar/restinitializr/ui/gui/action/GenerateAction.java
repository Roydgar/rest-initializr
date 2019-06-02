package tk.roydgar.restinitializr.ui.gui.action;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.service.Generator;
import tk.roydgar.restinitializr.ui.gui.Session;
import tk.roydgar.restinitializr.ui.gui.composer.UserInputComposer;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@RequiredArgsConstructor
public class GenerateAction implements ActionListener {

    private final UserInputComposer userInputComposer;
    private final Generator generator;
    private final Session session;


    @Override
    public void actionPerformed(ActionEvent e) {
        GeneratorParameters generatorParameters = userInputComposer.composeUserInput();
        try {
            ExtendableZipFile extendableZipFile = generator.generate(generatorParameters, session.getSqlTables());
            extendableZipFile.unzipTo(generatorParameters.getOutputDirectory());
            JOptionPane.showMessageDialog(null, "Success. Unzipped to: "
                            + generatorParameters.getOutputDirectory(), "Success", JOptionPane.ERROR_MESSAGE);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Generation failed: " + exception.toString(), "Fail"
                    , JOptionPane.ERROR_MESSAGE);
        }
    }

}
