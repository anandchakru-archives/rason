package rason.app.rest;

import static rason.app.util.RasonConstant.BEAN_JSON_CACHE;
import static rason.app.util.RasonConstant.HB_PREFIX;
import static rason.app.util.RasonConstant.URI_BASE;
import static rason.app.util.RasonConstant.URI_HB;
import static rason.app.util.RasonConstant.URI_STATS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.benmanes.caffeine.cache.Cache;
import rason.app.model.CacheStatsResponse;
import rason.app.model.JsonVal;
import rason.app.model.StrResponse;
import rason.app.model.StringKey;
import rason.app.service.RasonSettings;

@RestController
public class MetaController {
	@Qualifier(value = BEAN_JSON_CACHE)
	@Autowired
	private Cache<StringKey, JsonVal> jsonCache;
	@Autowired
	private RasonSettings settings;

	@GetMapping(value = { URI_BASE, URI_HB }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StrResponse hb() {
		return new StrResponse(HB_PREFIX + System.currentTimeMillis());
	}
	@GetMapping(value = URI_STATS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CacheStatsResponse stats() {
		return new CacheStatsResponse(jsonCache.estimatedSize(), settings.getMaxCacheSize(),
				settings.getMaxCacheLifeMinutes());
	}
}