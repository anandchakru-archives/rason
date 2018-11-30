package rason.app;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static rason.app.util.RasonConstant.NOT_FOUND;
import static rason.app.util.RasonConstant.URI_API;
import static rason.app.util.RasonConstant.URI_BASE;
import static rason.app.util.RasonConstant.URI_BU_LIST;
import static rason.app.util.RasonConstant.URI_BU_MAP;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import rason.app.model.BulkUploadRsp;
import rason.app.model.StringKey;

public class TestUtil {
	private static final ObjectMapper json = new ObjectMapper();
	public static final String JSON_1 = "{\"test\":\"simpleJsonForTest\"}";
	public static final String JSON_2 = "{\"test2\":\"UpdatedJsonForTest\"}";
	public static final String JSON_INVALID = "{\"test2\":\"UpdatedJsonForTest\"::}";
	public static final String SLUG_404 = "{\"fault\":\"" + NOT_FOUND + "\"}";
	public static final Map<String, JsonNode> BUM = Maps.newHashMap();
	public static final List<JsonNode> BUL = Lists.newArrayList();
	static {
		try {
			BUM.put("one", json.readTree(JSON_1));
			BUM.put("two", json.readTree(JSON_2));
			BUL.add(json.readTree(JSON_1));
			BUL.add(json.readTree(JSON_2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static StringKey create(final MockMvc mockMvc) {
		return mockRequest(mockMvc, URI_API, StringKey.class, true, JSON_1);
	}
	public static StringKey create(final MockMvc mockMvc, String payload) {
		return mockRequest(mockMvc, URI_API, StringKey.class, true, payload);
	}
	public static String update(final String key, String value, final MockMvc mockMvc)
			throws UnsupportedEncodingException, Exception {
		String ukey = mockMvc
				.perform(put(URI_API + URI_BASE + key).header(ACCEPT, APPLICATION_JSON)
						.header(CONTENT_TYPE, APPLICATION_JSON).content(value))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertTrue(StringUtils.isNotEmpty(ukey));
		assertNotNull(json.readValue(ukey, StringKey.class));
		return json.readValue(ukey, StringKey.class).getSlug();
	}
	public static String read(final String key, final MockMvc mockMvc) {
		return mockGet(mockMvc, URI_API + URI_BASE + key, JsonNode.class, true).toString();
	}
	public static String read(final String key, final MockMvc mockMvc, Boolean assrt) {
		return mockGet(mockMvc, URI_API + URI_BASE + key, JsonNode.class, assrt).toString();
	}
	public static String del(final String key, final MockMvc mockMvc) throws UnsupportedEncodingException, Exception {
		String dKey = mockMvc
				.perform(delete(URI_API + URI_BASE + key).header(ACCEPT, APPLICATION_JSON).header(CONTENT_TYPE,
						APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertTrue(StringUtils.isNotEmpty(dKey));
		assertNotNull(json.readValue(dKey, StringKey.class));
		return json.readValue(dKey, StringKey.class).getSlug();
	}
	public static BulkUploadRsp bum(final MockMvc mockMvc) {
		String payload = "{}";
		try {
			payload = json.writeValueAsString(BUM);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return mockRequest(mockMvc, URI_BU_MAP, BulkUploadRsp.class, true, payload);
	}
	public static BulkUploadRsp bul(final MockMvc mockMvc) {
		String payload = "{}";
		try {
			payload = json.writeValueAsString(BUL);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return mockRequest(mockMvc, URI_BU_LIST, BulkUploadRsp.class, true, payload);
	}
	public static <T> T mockGet(final MockMvc mockMvc, String uri, Class<T> rspType, Boolean assrt) {
		return mockRequest(mockMvc, uri, rspType, assrt, null);
	}
	public static <T> T mockPost(final MockMvc mockMvc, String uri, Class<T> rspType, Boolean assrt, String payload) {
		return mockRequest(mockMvc, uri, rspType, assrt, payload);
	}
	/**
	 * Convert JsonNode to respective Pojo
	 * 
	 * @param ip
	 * @param valueType
	 * @return
	 */
	public static <T> T to(JsonNode ip, Class<T> valueType) {
		try {
			return json.treeToValue(ip, valueType);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Make the actual GET/POST request. If payload is null GET, if not, POST.
	 * 
	 * @param mockMvc
	 * @param uri
	 * @param rspType
	 * @param assrt
	 * @param payload
	 * @return
	 */
	private static <T> T mockRequest(final MockMvc mockMvc, String uri, Class<T> rspType, Boolean assrt,
			String payload) {
		String body;
		try {
			MockHttpServletRequestBuilder rb = payload == null ? get(uri) : post(uri).content(payload);
			rb.header(ACCEPT, APPLICATION_JSON).header(CONTENT_TYPE, APPLICATION_JSON);
			body = mockMvc.perform(rb).andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
					.getContentAsString();
			if (assrt) {
				assertTrue(StringUtils.isNotEmpty(body));
				assertNotNull(json.readValue(body, rspType));
			}
			return json.readValue(body, rspType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
