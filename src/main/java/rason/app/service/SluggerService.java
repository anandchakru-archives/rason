package rason.app.service;

import static rason.app.util.RasonConstant.BEAN_SLUGGER;
import static rason.app.util.RasonConstant.DEFAULT_KEY;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import rason.app.model.JsonVal;
import rason.app.model.StringKey;

@Service(value = BEAN_SLUGGER)
public class SluggerService {
	@Autowired
	private RasonSettings settings;
	private Cache<String, Cache<StringKey, JsonVal>> buckets;
	private AtomicLong counter = new AtomicLong(1l);

	@PostConstruct()
	private void initializeBuckets() {
		this.buckets = Caffeine.newBuilder().maximumSize(settings.getMaxBuckets())
				.expireAfterWrite(settings.getMaxSessionLifeMinutes(), TimeUnit.MINUTES).build();
	}
	private Cache<StringKey, JsonVal> makeNewCache() {
		return Caffeine.newBuilder().maximumSize(settings.getMaxCacheSize())
				.expireAfterWrite(settings.getMaxCacheLifeMinutes(), TimeUnit.MINUTES)
				.removalListener(new com.github.benmanes.caffeine.cache.RemovalListener<StringKey, JsonVal>() {
					@Override
					public void onRemoval(StringKey key, JsonVal value, RemovalCause cause) {
						System.out.println("Evicted: " + key.getSlug() + "@" + cause);
					}
				}).build();
	}
	private JsonVal fetchValue(String bucketKey, StringKey sKey) {
		return cache(bucketKey).asMap().get(sKey);
	}
	public Cache<StringKey, JsonVal> cache(String bucketKey) {
		if (StringUtils.isEmpty(bucketKey)) {
			bucketKey = DEFAULT_KEY;
		}
		Cache<StringKey, JsonVal> cache = buckets.asMap().get(bucketKey);
		if (cache == null) {
			cache = makeNewCache();
			buckets.put(bucketKey, cache);
		}
		return cache;
	}
	public JsonVal value(String bucketKey, String key) {
		Object sKey = (StringUtils.isEmpty(key) || StringUtils.isBlank(key)
				|| StringUtils.length(key) > settings.getSlugMaxLength()) ? DEFAULT_KEY : new StringKey(key);
		return cache(bucketKey).asMap().get(sKey);
	}
	public StringKey keySlug(final String bucketKey, final String key) {
		StringKey sKey = (StringUtils.isEmpty(key) || StringUtils.isBlank(key)
				|| StringUtils.equalsIgnoreCase(key, DEFAULT_KEY))
				|| StringUtils.length(key) > settings.getSlugMaxLength() ? genKeySlug() : new StringKey(key);
		int i = 0;
		while (settings.getSlugGenMaxRetry() > i++ && fetchValue(bucketKey, sKey) != null) {
			sKey = genKeySlug();
		}
		return sKey;
	}
	public StringKey genKeySlug() {
		return new StringKey(String.valueOf(counter.addAndGet(1l)));
	}
}