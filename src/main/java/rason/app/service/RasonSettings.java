package rason.app.service;

import java.io.Serializable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@SuppressWarnings("serial")
@ConfigurationProperties(prefix = "rason", ignoreUnknownFields = true)
public class RasonSettings implements Serializable {
	private Integer slugMaxLength;
	private Integer slugGenMaxRetry;
	private Long maxBuckets;
	private Long maxCacheSize;
	private Integer maxSessionLifeMinutes;
	private Long maxCacheLifeMinutes;
}