package tk.roydgar.restinitializr.service.impl.goal;


import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.service.goal.AutomationBuildGoalExecutor;

import java.io.File;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class MavenGoalExecutor implements AutomationBuildGoalExecutor {

    private final Invoker mavenInvoker;
    private static final String POM_XML = "pom.xml";

    @Override
    @SneakyThrows
    public void cleanInstall(String projectLocation) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( new File( projectLocation + File.separator + POM_XML) );
        request.setGoals(ImmutableList.of("clean", "install"));

        mavenInvoker.execute(request);
    }

    @Override
    @SneakyThrows
    public void runSpringBootApp(String projectLocation) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( new File( projectLocation + File.separator + POM_XML ) );
        request.setGoals(Collections.singletonList("spring-boot:run"));

        mavenInvoker.execute(request);
    }
}
