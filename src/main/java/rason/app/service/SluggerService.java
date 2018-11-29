package rason.app.service;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.cache.Cache;
import rason.app.model.StringKey;

@Service
public class SluggerService {
	@Autowired
	private Cache<StringKey, JsonNode> cache;
	@Autowired
	private RasonSettings settings;
	private final String DEFAULT_KEY = "rnd";

	public StringKey slug(String key) {
		StringKey sKey = (StringUtils.isEmpty(key) || StringUtils.isBlank(key)
				|| StringUtils.equalsIgnoreCase(key, DEFAULT_KEY)) ? gen() : new StringKey(key);
		int i = 0;
		while (settings.getSlugGenMaxRetry() > i++ && cache.asMap().get(sKey) != null) {
			sKey = gen();
		}
		System.out.println(i);
		return sKey;
	}
	public StringKey gen() {
		return new StringKey(UUID.randomUUID().toString().replace("-", "").substring(0, 5));
	}
}