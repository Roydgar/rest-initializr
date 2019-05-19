package tk.roydgar.restinitializr.service;

import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

public interface GeneratorService {

    ExtendableZipFile generate(GeneratorParameters generatorParameters);

}
