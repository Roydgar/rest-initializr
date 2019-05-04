package tk.roydgar.restinitializr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties

public class RestInitializrApplication {


	public static void main(String[] args) {
		SpringApplication.run(RestInitializrApplication.class, args);
	}

}
