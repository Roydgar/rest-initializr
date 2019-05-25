package tk.roydgar.restinitializr.config;

import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AutomationBuildConfig {

    @Bean
    public Invoker mavenInvoker() {
        return new DefaultInvoker();
    }

}
