package rason.app.config;

import static rason.app.util.RasonConstant.BEAN_JSON_OBJECMAPPER;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan({ "rason.app" })
public class RasonConfig implements WebMvcConfigurer {
	@Bean(name = { BEAN_JSON_OBJECMAPPER })
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}