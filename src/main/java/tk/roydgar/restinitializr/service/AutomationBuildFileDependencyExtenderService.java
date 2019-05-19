package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.util.ExtendableZipFile;

public interface AutomationBuildFileDependencyExtenderService {

    void addDependencies(ExtendableZipFile extendableZipFile, String artifactName);

}
