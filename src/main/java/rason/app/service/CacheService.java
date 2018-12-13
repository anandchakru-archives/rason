package rason.app.service;

import static rason.app.util.RasonConstant.BEAN_CACHE;
import static rason.app.util.RasonConstant.BEAN_JSON_OBJECMAPPER;
import static rason.app.util.RasonConstant.BUCKET_KEY_MAX_LENGTH;
import static rason.app.util.RasonConstant.BUCKET_KEY_MIN_LENGTH;
import static rason.app.util.RasonConstant.NOT_FOUND;
import static rason.app.util.RasonConstant.SLUG_KEY_MAX_LENGTH;
import static rason.app.util.RasonConstant.SLUG_KEY_MIN_LENGTH;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import rason.app.model.BucketSlug;
import rason.app.model.BucketSlugs;
import rason.app.model.CacheStatsResponse;
import rason.app.model.JsonVal;
import rason.app.model.RasonException;

@Service(value = BEAN_CACHE)
public class CacheService {
	@Autowired
	private RasonSettings settings;
	@Autowired
	@Qualifier(BEAN_JSON_OBJECMAPPER)
	public ObjectMapper objectMapper;
	private Cache<String, Cache<String, JsonVal>> buckets;

	@PostConstruct()
	private void initializeBuckets() {
		this.buckets = Caffeine.newBuilder().maximumSize(settings.getMaxBuckets())
				.expireAfterWrite(settings.getMaxSessionLifeMinutes(), TimeUnit.MINUTES).build();
	}
	private String bucket(String bucket) {
		if (StringUtils.length(bucket) < BUCKET_KEY_MIN_LENGTH || StringUtils.length(bucket) > BUCKET_KEY_MAX_LENGTH
				|| !StringUtils.isAlphanumeric(bucket)) {
			bucket = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7);
		}
		return bucket;
	}
	private String slug(String bucket, String slug) {
		if (!StringUtils.isAlphanumeric(slug) || StringUtils.length(slug) < SLUG_KEY_MIN_LENGTH
				|| StringUtils.length(slug) > SLUG_KEY_MAX_LENGTH) {
			slug = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7);
		} else if (!exists(bucket, slug)) {
			return slug;
		}
		int i = 0;
		do {
			slug = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7);
		} while (settings.getSlugGenMaxRetry() > i++ && !exists(bucket, slug));
		return slug;
	}
	private BucketSlug cleanse(String bucket, String slug) {
		bucket = bucket(bucket);
		slug = slug(bucket, slug);
		return new BucketSlug(bucket, slug);
	}
	private Function<? super String, ? extends Cache<String, JsonVal>> makeBucket() {
		return (tmp) -> {
			return Caffeine.newBuilder().maximumSize(settings.getMaxCacheSize())
					.expireAfterWrite(settings.getMaxCacheLifeMinutes(), TimeUnit.MINUTES).recordStats().build();
		};
	}
	private Function<? super String, ? extends JsonVal> makeJsonVal() {
		return (tmp) -> {
			return new JsonVal(objectMapper.createObjectNode().put("fault", NOT_FOUND));
		};
	}
	public Boolean exists(String bucket, String slug) {
		return buckets.get(bucket, makeBucket()).getIfPresent(slug) != null;
	}
	public BucketSlug put(String bucket, String slug, JsonVal val) {
		BucketSlug key = this.cleanse(bucket, slug);
		this.buckets.get(key.getBucket(), makeBucket()).put(key.getSlug(), val);
		return key;
	}
	public BucketSlug update(String bucket, String slug, JsonVal val) {
		this.buckets.get(bucket, makeBucket()).put(slug, val);
		return new BucketSlug(bucket, slug);
	}
	public BucketSlugs putAll(String bucket, Map<String, JsonNode> values) {
		BucketSlugs bulkUploadRsp = new BucketSlugs();
		bucket = this.bucket(bucket);
		bulkUploadRsp.setBucket(bucket);
		Iterator<String> iSlugKeys = values.keySet().iterator();
		while (iSlugKeys.hasNext()) {
			String reqSlugKey = iSlugKeys.next();
			String rspSlugKey = this.slug(bucket, reqSlugKey);
			this.buckets.get(bucket, makeBucket()).put(rspSlugKey, new JsonVal(values.get(reqSlugKey)));
			bulkUploadRsp.add(reqSlugKey, rspSlugKey);
		}
		return bulkUploadRsp;
	}
	public BucketSlugs putAll(String bucket, List<JsonNode> values) {
		BucketSlugs bulkUploadRsp = new BucketSlugs();
		bucket = this.bucket(bucket);
		bulkUploadRsp.setBucket(bucket);
		Iterator<JsonNode> iSlugKeys = values.iterator();
		while (iSlugKeys.hasNext()) {
			String rspSlugKey = this.slug(bucket, null);
			this.buckets.get(bucket, makeBucket()).put(rspSlugKey, new JsonVal(iSlugKeys.next()));
			bulkUploadRsp.add(rspSlugKey, rspSlugKey);
		}
		return bulkUploadRsp;
	}
	public JsonNode random(String bucket) {
		JsonVal val = new JsonVal(objectMapper.createObjectNode().put("address", slug(bucket, null))
				.put("id", slug(bucket, null)).put("fname", slug(bucket, null)).put("lname", slug(bucket, null)));
		BucketSlug put = this.put(bucket, null, val);
		return this.get(bucket, put.getSlug());
	}
	public JsonNode get(String bucket, String slug) {
		JsonVal jsonVal = buckets.get(bucket, makeBucket()).get(slug, makeJsonVal());
		if (jsonVal.getVal() == null) {
			throw new RasonException(NOT_FOUND);
		}
		return jsonVal.getVal();
	}
	public BucketSlug delete(String bucket, String key) {
		this.buckets.get(bucket, makeBucket()).invalidate(key);
		return new BucketSlug(bucket, key);
	}
	public BucketSlugs keys(String bucket) {
		BucketSlugs response = new BucketSlugs();
		response.setBucket(bucket);
		response.setSlugsFromSet(this.buckets.get(bucket, makeBucket()).asMap().keySet());
		return response;
	}
	public CacheStatsResponse stats(String bucket) {
		return new CacheStatsResponse(buckets.get(bucket, makeBucket()).estimatedSize(), settings.getMaxCacheSize(),
				settings.getMaxCacheLifeMinutes());
	}
}