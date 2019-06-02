package tk.roydgar.restinitializr.service.goal;

public interface AutomationBuildGoalExecutor {
    void cleanBuild(String projectLocation);
    void runSpringBoot(String projectLocation);
}
