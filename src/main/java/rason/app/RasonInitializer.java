package rason.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rason.app.config.RasonConfig;

@SpringBootApplication
public class RasonInitializer {
	public static void main(String[] args) {
		SpringApplication.run(RasonConfig.class, args);
	}
}
