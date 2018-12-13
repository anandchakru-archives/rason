package rason.app.rest;

import static rason.app.util.RasonConstant.BEAN_CACHE;
import static rason.app.util.RasonConstant.URI_API;
import static rason.app.util.RasonConstant.URI_API_KEYS;
import static rason.app.util.RasonConstant.URI_API_WITH_KEY;
import static rason.app.util.RasonConstant.URI_BU_LIST;
import static rason.app.util.RasonConstant.URI_BU_MAP;
import static rason.app.util.RasonConstant.URI_EXISTS;
import static rason.app.util.RasonConstant.URI_RANDOM;
import static rason.app.util.RasonConstant.URI_STATS;
import java.util.List;
import java.util.Map;
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
import rason.app.model.BucketSlug;
import rason.app.model.BucketSlugs;
import rason.app.model.CacheStatsResponse;
import rason.app.model.CheckSlugRsp;
import rason.app.model.JsonVal;
import rason.app.service.CacheService;

@RestController
public class ApiController {
	@Autowired
	@Qualifier(value = BEAN_CACHE)
	private CacheService slugger;

	@GetMapping(value = URI_EXISTS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CheckSlugRsp exists(@PathVariable String bucket, @PathVariable String slug) {
		return new CheckSlugRsp(bucket, slugger.exists(bucket, slug));
	}
	@PostMapping(value = URI_RANDOM, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody JsonNode random(@PathVariable String bucket) {
		return slugger.random(bucket);
	}
	@PostMapping(value = URI_API, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlug create(@PathVariable String bucket, @RequestBody JsonNode value) {
		return create(bucket, null, value);
	}
	@PostMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlug create(@PathVariable String bucket, @PathVariable String slug,
			@RequestBody JsonNode value) {
		return slugger.put(bucket, slug, new JsonVal(value));
	}
	@PutMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlug update(@PathVariable String bucket, @PathVariable String slug,
			@RequestBody JsonNode value) {
		return slugger.update(bucket, slug, new JsonVal(value));
	}
	@GetMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody JsonNode read(@PathVariable String bucket, @PathVariable String slug) {
		return slugger.get(bucket, slug);
	}
	@DeleteMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlug delete(@PathVariable String bucket, @PathVariable String slug) {
		return slugger.delete(bucket, slug);
	}
	@PostMapping(value = URI_BU_MAP, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlugs bulkMap(@PathVariable String bucket, @RequestBody Map<String, JsonNode> values) {
		return this.slugger.putAll(bucket, values);
	}
	@PostMapping(value = URI_BU_LIST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlugs bulkList(@PathVariable String bucket, @RequestBody List<JsonNode> values) {
		return this.slugger.putAll(bucket, values);
	}
	@GetMapping(value = URI_API_KEYS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BucketSlugs keys(@PathVariable String bucket) {
		return slugger.keys(bucket);
	}
	@GetMapping(value = URI_STATS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CacheStatsResponse stats(@PathVariable String bucket) {
		return slugger.stats(bucket);
	}
}