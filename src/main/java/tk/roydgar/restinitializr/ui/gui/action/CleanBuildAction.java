package tk.roydgar.restinitializr.ui.gui.action;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.enums.AutomationBuildSystem;
import tk.roydgar.restinitializr.service.goal.AutomationBuildGoalExecutor;
import tk.roydgar.restinitializr.ui.gui.composer.UserInputComposer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CleanBuildAction implements ActionListener {

    private final Map<AutomationBuildSystem, AutomationBuildGoalExecutor> automationBuildGoalExecutorMap;
    private final UserInputComposer userInputComposer;


    @Override
    public void actionPerformed(ActionEvent e) {
        GeneratorParameters generatorParameters = userInputComposer.composeUserInput();
        AutomationBuildGoalExecutor goalExecutor = automationBuildGoalExecutorMap
                .get(generatorParameters.getProjectParameters().getAutomationBuildSystem());

        String projectRootDir = getProjectRootDir(generatorParameters.getOutputDirectory(),
                generatorParameters.getProjectParameters().getArtifactId());
        goalExecutor.cleanBuild(projectRootDir);
    }

    private String getProjectRootDir(String outputDir, String artifactId) {
        return outputDir + File.separator + artifactId;
    }

}
