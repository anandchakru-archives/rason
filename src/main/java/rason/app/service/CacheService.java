package rason.app.service;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static rason.app.util.RasonConstant.BEAN_CACHE;
import static rason.app.util.RasonConstant.SESSION_CACHE;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import rason.app.model.JsonVal;
import rason.app.model.StringKey;

@Service(BEAN_CACHE)
@SuppressWarnings("unchecked")
@Scope(value = "session", proxyMode = TARGET_CLASS)
public class CacheService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RasonSettings settings;

	private HttpSession session() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
				.getSession(false);
	}
	private Cache<StringKey, JsonVal> jsonCache(final String sessionId) {
		return Caffeine.newBuilder().maximumSize(settings.getMaxCacheSize())
				.expireAfterWrite(settings.getMaxCacheLifeMinutes(), TimeUnit.MINUTES)
				.removalListener(new com.github.benmanes.caffeine.cache.RemovalListener<StringKey, JsonVal>() {
					@Override
					public void onRemoval(StringKey key, JsonVal value, RemovalCause cause) {
						logger.info("Evicted {}, in session: {}, becauseo of {}", key.getSlug(), sessionId, cause);
					}
				}).build();
	}
	public Cache<StringKey, JsonVal> cache() {
		Cache<StringKey, JsonVal> cache = (Cache<StringKey, JsonVal>) this.session().getAttribute(SESSION_CACHE);
		if (cache == null) {
			cache = jsonCache(this.session().getId());
			this.session().setAttribute(SESSION_CACHE, cache);
		}
		logger.debug("cache access @" + this.session().getId() + " ,has: " + cache.asMap().size());
		return cache;
	}
}
