package rason.app.rest;

import static rason.app.util.RasonConstant.BEAN_JSON_CACHE;
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
import com.google.common.cache.Cache;
import rason.app.model.BulkUploadRsp;
import rason.app.model.StringKey;
import rason.app.service.SluggerService;

@RestController
public class BulkUploadController {
	@Autowired
	@Qualifier(value = BEAN_JSON_CACHE)
	private Cache<StringKey, JsonNode> jsonCache;
	@Autowired
	@Qualifier(value = BEAN_SLUGGER)
	private SluggerService slugger;

	@PostMapping(value = URI_BU_MAP, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BulkUploadRsp bulkMap(@RequestBody Map<String, JsonNode> payload) {
		BulkUploadRsp rsp = new BulkUploadRsp();
		if (payload != null && !payload.isEmpty()) {
			Iterator<String> iterator = payload.keySet().iterator();
			while (iterator.hasNext()) {
				String reqKey = iterator.next();
				StringKey sKey = slugger.slug(reqKey);
				jsonCache.put(sKey, payload.get(reqKey));
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
				jsonCache.put(sKey, value);
				rsp.add(sKey.getSlug(), sKey);
			}
		}
		return rsp;
	}
}