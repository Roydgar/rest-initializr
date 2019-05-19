package tk.roydgar.restinitializr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.config.properties.DependencyProperties;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

@Service
@RequiredArgsConstructor
public class GradleFileDependencyExtenderService implements AutomationBuildFileDependencyExtenderService {

    private final DependencyExtenderService dependencyExtenderService;
    private final DependencyProperties dependencyProperties;

    @Override
    public void addDependencies(ExtendableZipFile extendableZipFile, String artifactName) {
        dependencyProperties.getGradleDependencies().forEach(dependency -> dependencyExtenderService.addDependency(
                extendableZipFile, "build.gradle", "dependencies {", artifactName, dependency));
    }

}
