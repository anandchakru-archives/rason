package rason.app.config;

import static rason.app.util.RasonConstant.BEAN_JSON_OBJECMAPPER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;
import rason.app.service.RasonSettings;

@Configuration
@ComponentScan({ "rason.app" })
public class RasonConfig implements WebMvcConfigurer {
	@Autowired
	private RasonSettings settings;

	@Bean(name = { BEAN_JSON_OBJECMAPPER })
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}