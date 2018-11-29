package rason.app.rest;

import static rason.app.util.RasonConstant.URI_API;
import static rason.app.util.RasonConstant.URI_API_WITH_KEY;
import static rason.app.util.RasonConstant.NOT_FOUND;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.google.common.cache.Cache;
import rason.app.model.RasonException;
import rason.app.model.StringKey;
import rason.app.service.SluggerService;

@RestController
public class ApiController {
	@Autowired
	private Cache<StringKey, JsonNode> cache;
	@Autowired
	private SluggerService slugger;

	@PostMapping(value = URI_API, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey create(@RequestBody JsonNode value) {
		return create(null, value);
	}
	@PostMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey create(@PathVariable String key, @RequestBody JsonNode value) {
		StringKey sKey = slugger.slug(key);
		cache.put(sKey, value);
		return sKey;
	}
	@PutMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey update(@PathVariable String key, @RequestBody JsonNode value) {
		StringKey sKey = new StringKey(key);
		cache.put(sKey, value);
		return sKey;
	}
	@GetMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody JsonNode read(@PathVariable String key) {
		StringKey sKey = new StringKey(key);
		JsonNode jsonNode = cache.asMap().get(sKey);
		if (jsonNode == null) {
			throw new RasonException(NOT_FOUND);
		}
		return jsonNode;
	}
	@DeleteMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey delete(@PathVariable String key) {
		StringKey sKey = new StringKey(key);
		cache.invalidate(sKey);
		return sKey;
	}
}