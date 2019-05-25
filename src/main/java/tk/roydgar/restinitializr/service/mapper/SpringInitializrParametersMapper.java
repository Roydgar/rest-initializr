package tk.roydgar.restinitializr.service.mapper;

import tk.roydgar.restinitializr.model.ProjectParameters;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;

public interface SpringInitializrParametersMapper {
    SpringInitializrParameters toSpringInitializrParameters(ProjectParameters projectParameters);
}
