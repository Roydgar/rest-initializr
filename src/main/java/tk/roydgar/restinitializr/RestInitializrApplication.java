package tk.roydgar.restinitializr;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import tk.roydgar.restinitializr.ui.gui.GUIFrame;

import java.awt.*;

@SpringBootApplication
@EnableAutoConfiguration
@Profile("gui")
public class RestInitializrApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(RestInitializrApplication.class)
				.headless(false).run(args);

		EventQueue.invokeLater(() -> {
			GUIFrame guiFrame = ctx.getBean(GUIFrame.class);
			guiFrame.setVisible(true);
		});
	}

}
