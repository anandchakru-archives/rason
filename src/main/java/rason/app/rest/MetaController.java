package rason.app.rest;

import static rason.app.util.RasonConstant.HB_PREFIX;
import static rason.app.util.RasonConstant.URI_BASE;
import static rason.app.util.RasonConstant.URI_HB;
import static rason.app.util.RasonConstant.URI_STATS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rason.app.model.CacheStatsResponse;
import rason.app.model.StrResponse;
import rason.app.service.RasonSettings;
import rason.app.service.SluggerService;

@RestController
public class MetaController {
	@Autowired
	private SluggerService slugger;
	@Autowired
	private RasonSettings settings;

	@GetMapping(value = { URI_BASE, URI_HB }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StrResponse hb() {
		return new StrResponse(HB_PREFIX + System.currentTimeMillis());
	}
	@GetMapping(value = URI_STATS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CacheStatsResponse stats(@PathVariable(name = "bucketId") String bucketKey) {
		return new CacheStatsResponse(slugger.cache(bucketKey).estimatedSize(), settings.getMaxCacheSize(),
				settings.getMaxCacheLifeMinutes());
	}
}