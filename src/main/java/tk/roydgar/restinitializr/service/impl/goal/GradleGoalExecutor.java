package tk.roydgar.restinitializr.service.impl.goal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.service.goal.AutomationBuildGoalExecutor;

import java.io.File;

@Component
@RequiredArgsConstructor
public class GradleGoalExecutor implements AutomationBuildGoalExecutor {
    private static String GRADLEW_EXECUTABLE = "gradlew.bat";
    private static String BALNK = " ";

    @Override
    @SneakyThrows
    public void cleanInstall(String projectLocation) {
        String command = projectLocation + File.separator + GRADLEW_EXECUTABLE + BALNK + "jar";
        Runtime.getRuntime().exec(command);
    }

    @Override
    @SneakyThrows
    public void runSpringBootApp(String projectLocation) {
        String command = projectLocation + File.separator + GRADLEW_EXECUTABLE + BALNK + "bootRun";
        Runtime.getRuntime().exec(command);
    }
}
