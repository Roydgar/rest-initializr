package tk.roydgar.restinitializr.service.dependency;

import tk.roydgar.restinitializr.util.ExtendableZipFile;

public interface AutomationBuildFileDependencyExtender {

    void addDependencies(ExtendableZipFile extendableZipFile, String artifactName);

}
