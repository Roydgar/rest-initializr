package tk.roydgar.restinitializr.service.impl.goal;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.service.goal.AutomationBuildGoalExecutor;

import java.io.File;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class GradleGoalExecutor implements AutomationBuildGoalExecutor {
    private final Runtime runtime;
    private final static String COMMAND_FORMAT = "cmd.exe /c start %s";


    @Override
    @SneakyThrows
    public void cleanBuild(String projectLocation) {
        runtime.exec(format(COMMAND_FORMAT, "./gradlew clean build"), null, new File(projectLocation));
    }

    @Override
    @SneakyThrows
    public void runSpringBoot(String projectLocation) {
        runtime.exec(format(COMMAND_FORMAT, "./gradlew bootRun"), null, new File(projectLocation));
    }
}

