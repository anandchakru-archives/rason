package rason.app.rest;

import static rason.app.util.RasonConstant.BEAN_JSON_OBJECMAPPER;
import static rason.app.util.RasonConstant.BEAN_SLUGGER;
import static rason.app.util.RasonConstant.DEFAULT_KEY;
import static rason.app.util.RasonConstant.NOT_FOUND;
import static rason.app.util.RasonConstant.URI_API;
import static rason.app.util.RasonConstant.URI_API_KEYS;
import static rason.app.util.RasonConstant.URI_API_WITH_KEY;
import static rason.app.util.RasonConstant.URI_BASE;
import static rason.app.util.RasonConstant.URI_BU_LIST;
import static rason.app.util.RasonConstant.URI_BU_MAP;
import static rason.app.util.RasonConstant.URI_CHECK_SLUG;
import static rason.app.util.RasonConstant.URI_STATS;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import rason.app.model.BucketSlugRsp;
import rason.app.model.BulkUploadRsp;
import rason.app.model.CacheStatsResponse;
import rason.app.model.CheckSlugRsp;
import rason.app.model.JsonVal;
import rason.app.model.RasonException;
import rason.app.model.StrResponse;
import rason.app.model.StringKey;
import rason.app.service.RasonSettings;
import rason.app.service.SluggerService;

@RestController
public class ApiController {
	@Autowired
	@Qualifier(value = BEAN_SLUGGER)
	private SluggerService slugger;
	@Autowired
	@Qualifier(BEAN_JSON_OBJECMAPPER)
	public ObjectMapper objectMapper;
	@Autowired
	private RasonSettings settings;

	@GetMapping(value = URI_CHECK_SLUG, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CheckSlugRsp exists(@PathVariable(name = "bucketId") String bucketKey,
			@PathVariable String key) {
		return new CheckSlugRsp(bucketKey, slugger.cache(bucketKey).asMap().containsKey(new StringKey(key)));
	}
	@PostMapping(value = URI_API, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlugRsp create(@PathVariable(name = "bucketId") String bucketKey,
			@RequestBody JsonNode value) {
		return create(bucketKey, null, value);
	}
	@PostMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlugRsp create(@PathVariable(name = "bucketId") String bucketKey,
			@PathVariable String key, @RequestBody JsonNode value) {
		StringKey sKey = slugger.keySlug(bucketKey, key);
		slugger.cache(bucketKey).put(sKey, new JsonVal(value));
		BucketSlugRsp bucketSlugRsp = new BucketSlugRsp(bucketKey);
		bucketSlugRsp.setSlug(sKey.getSlug());
		return bucketSlugRsp;
	}
	@PutMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlugRsp update(@PathVariable(name = "bucketId") String bucketKey,
			@PathVariable String key, @RequestBody JsonNode value) {
		StringKey sKey = new StringKey(key);
		slugger.cache(bucketKey).put(sKey, new JsonVal(value));
		BucketSlugRsp bucketSlugRsp = new BucketSlugRsp(bucketKey);
		bucketSlugRsp.setSlug(sKey.getSlug());
		return bucketSlugRsp;
	}
	@GetMapping(value = URI_API + URI_BASE + DEFAULT_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlugRsp makeRandom(@PathVariable(name = "bucketId") String bucketKey) {
		StringKey sKey = slugger.keySlug(bucketKey, null);
		StrResponse rsp = new StrResponse();
		rsp.setPayload(UUID.randomUUID().toString().replace("-", ""));
		slugger.cache(bucketKey).put(sKey, new JsonVal(objectMapper.valueToTree(rsp)));
		BucketSlugRsp bucketSlugRsp = new BucketSlugRsp(bucketKey);
		bucketSlugRsp.setSlug(sKey.getSlug());
		return bucketSlugRsp;
	}
	@GetMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody JsonNode read(@PathVariable(name = "bucketId") String bucketKey, @PathVariable String key) {
		StringKey sKey = new StringKey(key);
		JsonVal jsonVal = slugger.cache(bucketKey).asMap().get(sKey);
		if (jsonVal == null) {
			throw new RasonException(NOT_FOUND);
		}
		JsonNode jsonNode = jsonVal.getVal();
		if (jsonNode == null) {
			throw new RasonException(NOT_FOUND);
		}
		return jsonNode;
	}
	@GetMapping(value = URI_API_KEYS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Set<StringKey> keys(@PathVariable(name = "bucketId") String bucketKey) {
		return slugger.cache(bucketKey).asMap().keySet();
	}
	@DeleteMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlugRsp delete(@PathVariable(name = "bucketId") String bucketKey,
			@PathVariable String key) {
		StringKey sKey = new StringKey(key);
		slugger.cache(bucketKey).invalidate(sKey);
		BucketSlugRsp bucketSlugRsp = new BucketSlugRsp(bucketKey);
		bucketSlugRsp.setSlug(sKey.getSlug());
		return bucketSlugRsp;
	}
	@PostMapping(value = URI_BU_MAP, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BulkUploadRsp bulkMap(@PathVariable(name = "bucketId") String bucketKey,
			@RequestBody Map<String, JsonNode> payload) {
		BulkUploadRsp rsp = new BulkUploadRsp();
		if (payload != null && !payload.isEmpty()) {
			Iterator<String> iterator = payload.keySet().iterator();
			while (iterator.hasNext()) {
				String reqKey = iterator.next();
				StringKey sKey = slugger.keySlug(bucketKey, reqKey);
				slugger.cache(bucketKey).put(sKey, new JsonVal(payload.get(reqKey)));
				rsp.add(reqKey, sKey);
			}
		}
		return rsp;
	}
	@PostMapping(value = URI_BU_LIST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BulkUploadRsp create(@PathVariable(name = "bucketId") String bucketKey,
			@RequestBody List<JsonNode> payload) {
		BulkUploadRsp rsp = new BulkUploadRsp();
		if (payload != null && !payload.isEmpty()) {
			for (JsonNode value : payload) {
				StringKey sKey = slugger.keySlug(bucketKey, null);
				slugger.cache(bucketKey).put(sKey, new JsonVal(value));
				rsp.add(sKey.getSlug(), sKey);
			}
		}
		return rsp;
	}
	@GetMapping(value = URI_STATS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CacheStatsResponse stats(@PathVariable(name = "bucketId") String bucketKey) {
		return new CacheStatsResponse(slugger.cache(bucketKey).estimatedSize(), settings.getMaxCacheSize(),
				settings.getMaxCacheLifeMinutes());
	}
}