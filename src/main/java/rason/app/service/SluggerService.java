package rason.app.service;

import static rason.app.util.RasonConstant.BEAN_JSON_CACHE;
import static rason.app.util.RasonConstant.BEAN_SLUGGER;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.cache.Cache;
import rason.app.model.StringKey;

@Service(value = BEAN_SLUGGER)
public class SluggerService {
	@Qualifier(value = BEAN_JSON_CACHE)
	@Autowired
	private Cache<StringKey, JsonNode> jsonCache;
	@Autowired
	private RasonSettings settings;
	private final String DEFAULT_KEY = "rnd";

	public StringKey slug(String key) {
		StringKey sKey = (StringUtils.isEmpty(key) || StringUtils.isBlank(key)
				|| StringUtils.equalsIgnoreCase(key, DEFAULT_KEY)) ? gen() : new StringKey(key);
		int i = 0;
		while (settings.getSlugGenMaxRetry() > i++ && jsonCache.asMap().get(sKey) != null) {
			sKey = gen();
		}
		return sKey;
	}
	public StringKey gen() {
		return new StringKey(UUID.randomUUID().toString().replace("-", "").substring(0, 5));
	}
}