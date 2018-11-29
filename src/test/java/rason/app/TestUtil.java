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
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import rason.app.model.StringKey;

public class TestUtil {
	private static ObjectMapper json = new ObjectMapper();
	public static String JSON_1 = "{\"test\":\"simpleJsonForTest\"}";
	public static String JSON_2 = "{\"test2\":\"UpdatedJsonForTest\"}";
	public static String JSON_INVALID = "{\"test2\":\"UpdatedJsonForTest\"::}";
	public static String SLUG_404 = "{\"fault\":\"" + NOT_FOUND + "\"}";

	public static String create(final MockMvc mockMvc) throws UnsupportedEncodingException, Exception {
		return create(mockMvc, JSON_1);
	}
	public static String create(final MockMvc mockMvc, String payload) throws UnsupportedEncodingException, Exception {
		String key = mockMvc
				.perform(post(URI_API).header(ACCEPT, APPLICATION_JSON).header(CONTENT_TYPE, APPLICATION_JSON)
						.content(payload))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertTrue(StringUtils.isNotEmpty(key));
		assertNotNull(json.readValue(key, StringKey.class));
		return json.readValue(key, StringKey.class).getSlug();
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
	public static String read(final String key, final MockMvc mockMvc) throws UnsupportedEncodingException, Exception {
		return read(key, mockMvc, true);
	}
	public static String read(final String key, final MockMvc mockMvc, Boolean assrt)
			throws UnsupportedEncodingException, Exception {
		String value = mockMvc
				.perform(get(URI_API + URI_BASE + key).header(ACCEPT, APPLICATION_JSON).header(CONTENT_TYPE,
						APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		if (assrt) {
			assertTrue(StringUtils.isNotEmpty(value));
			assertNotNull(json.readValue(value, JsonNode.class));
		}
		return value;
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
	public static <T> T mockGet(final MockMvc mockMvc, String uri, Class<T> rspType, Boolean assrt) {
		String body;
		try {
			body = mockMvc.perform(get(uri).header(ACCEPT, APPLICATION_JSON).header(CONTENT_TYPE, APPLICATION_JSON))
					.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
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
