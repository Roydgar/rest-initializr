package tk.roydgar.restinitializr.service.goal;

public interface AutomationBuildGoalExecutor {
    void cleanInstall(String projectLocation);
    void runSpringBootApp(String projectLocation);
}
