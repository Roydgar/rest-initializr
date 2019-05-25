package tk.roydgar.restinitializr;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import tk.roydgar.restinitializr.ui.gui.GUIFrame;

import java.awt.*;

@SpringBootApplication
@EnableConfigurationProperties
public class RestInitializrApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(GUIFrame.class)
				.headless(false).run(args);

		EventQueue.invokeLater(() -> {
			GUIFrame ex = ctx.getBean(GUIFrame.class);
			ex.setVisible(true);
		});

	}

}
