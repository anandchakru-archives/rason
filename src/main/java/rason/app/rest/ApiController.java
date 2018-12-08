package rason.app.rest;

import static rason.app.util.RasonConstant.BEAN_CACHE;
import static rason.app.util.RasonConstant.BEAN_JSON_OBJECMAPPER;
import static rason.app.util.RasonConstant.BEAN_SLUGGER;
import static rason.app.util.RasonConstant.DEFAULT_KEY;
import static rason.app.util.RasonConstant.NOT_FOUND;
import static rason.app.util.RasonConstant.URI_API;
import static rason.app.util.RasonConstant.URI_API_KEYS;
import static rason.app.util.RasonConstant.URI_API_WITH_KEY;
import static rason.app.util.RasonConstant.URI_BASE;
import static rason.app.util.RasonConstant.URI_CHECK_SLUG;
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
import rason.app.model.CheckSlugRsp;
import rason.app.model.JsonVal;
import rason.app.model.RasonException;
import rason.app.model.StrResponse;
import rason.app.model.StringKey;
import rason.app.service.CacheService;
import rason.app.service.SluggerService;

@RestController
public class ApiController {
	@Autowired
	@Qualifier(value = BEAN_SLUGGER)
	private SluggerService slugger;
	@Autowired
	@Qualifier(BEAN_JSON_OBJECMAPPER)
	private ObjectMapper objectMapper;
	@Autowired
	@Qualifier(BEAN_CACHE)
	private CacheService cacheService;

	@GetMapping(value = URI_CHECK_SLUG, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CheckSlugRsp create(@PathVariable String key) {
		return new CheckSlugRsp(cacheService.cache().asMap().containsKey(new StringKey(key)));
	}
	@PostMapping(value = URI_API, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey create(@RequestBody JsonNode value) {
		return create(null, value);
	}
	@PostMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey create(@PathVariable String key, @RequestBody JsonNode value) {
		StringKey sKey = slugger.slug(key);
		cacheService.cache().put(sKey, new JsonVal(value));
		return sKey;
	}
	@PutMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey update(@PathVariable String key, @RequestBody JsonNode value) {
		StringKey sKey = new StringKey(key);
		cacheService.cache().put(sKey, new JsonVal(value));
		return sKey;
	}
	@GetMapping(value = URI_API + URI_BASE + DEFAULT_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey makeRandom() {
		StringKey key = slugger.slug(null);
		StrResponse rsp = new StrResponse();
		rsp.setPayload(UUID.randomUUID().toString().replace("-", ""));
		cacheService.cache().put(key, new JsonVal(objectMapper.valueToTree(rsp)));
		return key;
	}
	@GetMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody JsonNode read(@PathVariable String key) {
		StringKey sKey = new StringKey(key);
		JsonVal jsonVal = cacheService.cache().asMap().get(sKey);
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
	public @ResponseBody Set<StringKey> keys() {
		return cacheService.cache().asMap().keySet();
	}
	@DeleteMapping(value = URI_API_WITH_KEY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StringKey delete(@PathVariable String key) {
		StringKey sKey = new StringKey(key);
		cacheService.cache().invalidate(sKey);
		return sKey;
	}
}