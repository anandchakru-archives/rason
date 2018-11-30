package rason.app.config;

import static rason.app.util.RasonConstant.BEAN_JSON_CACHE;
import static rason.app.util.RasonConstant.BEAN_JSON_OBJECMAPPER;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import rason.app.model.StringKey;
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
	@Bean(name = { BEAN_JSON_CACHE })
	public Cache<StringKey, JsonNode> jsonCache() {
		return CacheBuilder.newBuilder().maximumSize(settings.getMaxCacheSize())
				.expireAfterWrite(settings.getMaxCacheLifeMinutes(), TimeUnit.MINUTES)
				.removalListener(new RemovalListener<StringKey, JsonNode>() {
					@Override
					public void onRemoval(RemovalNotification<StringKey, JsonNode> notification) {
						System.out.println("Evicted: " + notification.getKey().getSlug());
					}
				}).recordStats().build();
	}
}