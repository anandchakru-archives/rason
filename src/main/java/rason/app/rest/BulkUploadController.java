package rason.app.rest;

import static rason.app.util.RasonConstant.BEAN_CACHE;
import static rason.app.util.RasonConstant.BEAN_SLUGGER;
import static rason.app.util.RasonConstant.URI_BU_LIST;
import static rason.app.util.RasonConstant.URI_BU_MAP;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import rason.app.model.BulkUploadRsp;
import rason.app.model.JsonVal;
import rason.app.model.StringKey;
import rason.app.service.CacheService;
import rason.app.service.SluggerService;

@RestController
public class BulkUploadController {
	@Autowired
	@Qualifier(value = BEAN_SLUGGER)
	private SluggerService slugger;
	@Autowired
	@Qualifier(BEAN_CACHE)
	private CacheService cacheService;

	@PostMapping(value = URI_BU_MAP, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BulkUploadRsp bulkMap(@RequestBody Map<String, JsonNode> payload) {
		BulkUploadRsp rsp = new BulkUploadRsp();
		if (payload != null && !payload.isEmpty()) {
			Iterator<String> iterator = payload.keySet().iterator();
			while (iterator.hasNext()) {
				String reqKey = iterator.next();
				StringKey sKey = slugger.slug(reqKey);
				cacheService.cache().put(sKey, new JsonVal(payload.get(reqKey)));
				rsp.add(reqKey, sKey);
			}
		}
		return rsp;
	}
	@PostMapping(value = URI_BU_LIST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BulkUploadRsp create(@RequestBody List<JsonNode> payload) {
		BulkUploadRsp rsp = new BulkUploadRsp();
		if (payload != null && !payload.isEmpty()) {
			for (JsonNode value : payload) {
				StringKey sKey = slugger.slug(null);
				cacheService.cache().put(sKey, new JsonVal(value));
				rsp.add(sKey.getSlug(), sKey);
			}
		}
		return rsp;
	}
}