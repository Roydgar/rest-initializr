package tk.roydgar.restinitializr.service.impl.dependency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.roydgar.restinitializr.config.properties.DependencyProperties;
import tk.roydgar.restinitializr.service.dependency.AutomationBuildFileDependencyExtender;
import tk.roydgar.restinitializr.service.dependency.DependencyExtender;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

@Service
@RequiredArgsConstructor
public class MavenFileDependencyExtender implements AutomationBuildFileDependencyExtender {

    private final DependencyExtender dependencyExtender;
    private final DependencyProperties dependencyProperties;

    @Override
    public void addDependencies(ExtendableZipFile extendableZipFile, String artifactName) {
        dependencyProperties.getMavenDependencies().forEach(dependency -> dependencyExtender.addDependency(
                extendableZipFile, "pom.xml", "<dependencies>", artifactName, dependency));
    }

}
