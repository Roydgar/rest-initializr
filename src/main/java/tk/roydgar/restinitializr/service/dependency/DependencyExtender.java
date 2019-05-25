package tk.roydgar.restinitializr.service.dependency;

import tk.roydgar.restinitializr.util.ExtendableZipFile;

public interface DependencyExtender {

    void addDependency(ExtendableZipFile extendableZipFile, String buildFileName, String dependenciesKeyword, String artifactName, String dependency);

}
