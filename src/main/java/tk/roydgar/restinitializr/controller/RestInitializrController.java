package tk.roydgar.restinitializr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.roydgar.restinitializr.model.GeneratorParameters;
import tk.roydgar.restinitializr.model.GeneratorRestParameters;
import tk.roydgar.restinitializr.service.Generator;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.util.ExtendableZipFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Profile("web")
public class RestInitializrController {

    private final Generator generator;

    @PostMapping(value = "/generate", produces="application/zip")
    public byte[] generate(GeneratorRestParameters generatorRestParameters) throws IOException {
        List<SQLTable> sqlTables = generator.parseQueries(generatorRestParameters.getSqlInputQueries());

        GeneratorParameters generatorParameters = new GeneratorParameters();
        generatorParameters.setProjectParameters(generatorRestParameters.getProjectParameters());
        generatorParameters.setPropertiesParameters(generatorParameters.getPropertiesParameters());

        ExtendableZipFile extendableZipFile = generator.generate(generatorParameters, sqlTables);
        return extendableZipFile.toByteArray();
    }

}
