package rason.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Import;
import rason.app.config.RasonConfig;

@Import({ RasonConfig.class })
@SpringBootApplication
public class RasonInitializer {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplicationBuilder(RasonInitializer.class).build(args);
		app.addListeners(new ApplicationPidFileWriter());
		app.run();
	}
}
