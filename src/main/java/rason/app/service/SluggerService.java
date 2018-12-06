package rason.app.service;

import static rason.app.util.RasonConstant.BEAN_JSON_CACHE;
import static rason.app.util.RasonConstant.BEAN_SLUGGER;
import static rason.app.util.RasonConstant.DEFAULT_KEY;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Cache;
import rason.app.model.JsonVal;
import rason.app.model.StringKey;

@Service(value = BEAN_SLUGGER)
public class SluggerService {
	@Qualifier(value = BEAN_JSON_CACHE)
	@Autowired
	private Cache<StringKey, JsonVal> jsonCache;
	@Autowired
	private RasonSettings settings;
	private AtomicLong counter = new AtomicLong(1l);

	public StringKey slug(String key) {
		StringKey sKey = (StringUtils.isEmpty(key) || StringUtils.isBlank(key)
				|| StringUtils.equalsIgnoreCase(key, DEFAULT_KEY))
				|| StringUtils.length(key) > settings.getSlugMaxLength() ? gen() : new StringKey(key);
		int i = 0;
		while (settings.getSlugGenMaxRetry() > i++ && jsonCache.asMap().get(sKey) != null) {
			sKey = gen();
		}
		return sKey;
	}
	public StringKey gen() {
		return new StringKey(String.valueOf(counter.addAndGet(1l)));
	}
}