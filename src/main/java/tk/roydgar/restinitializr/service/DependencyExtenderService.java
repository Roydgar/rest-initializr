package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.util.ExtendableZipFile;

public interface DependencyExtenderService {

    void addDependency(ExtendableZipFile extendableZipFile, String buildFileName, String dependenciesKeyword, String artifactName, String dependency);

}
