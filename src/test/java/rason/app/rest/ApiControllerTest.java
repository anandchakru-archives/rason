package rason.app.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;
import static rason.app.TestUtil.BUCKET_ID;
import static rason.app.TestUtil.JSON_1;
import static rason.app.TestUtil.JSON_2;
import static rason.app.TestUtil.JSON_INVALID;
import static rason.app.TestUtil.SLUG_404;
import static rason.app.TestUtil.create;
import static rason.app.TestUtil.del;
import static rason.app.TestUtil.mockGet;
import static rason.app.TestUtil.read;
import static rason.app.TestUtil.update;
import static rason.app.util.RasonConstant.NOT_FOUND;
import static rason.app.util.RasonConstant.URI_API;
import static rason.app.util.RasonConstant.URI_BASE;
import static rason.app.util.RasonConstant.URI_BUCKET;
import static rason.app.util.RasonConstant.URI_RANDOM;
import static rason.app.util.RasonConstant.URI_STATS;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.JsonNode;
import rason.app.config.RasonConfig;
import rason.app.model.CacheStatsResponse;
import rason.app.model.FaultResponse;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import({ RasonConfig.class })
public class ApiControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void profile() {
		System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, "junit");
	}
	@Test
	public void testCreateJsonNode() {
		create(mockMvc);
	}
	@Test
	public void testCreateInvalidJsonNode() {
		assertNull(create(mockMvc, JSON_INVALID));
	}
	@Test
	public void testInvalidUrl() {
		FaultResponse fault = mockGet(mockMvc, URI_API.replace(URI_BUCKET, BUCKET_ID) + URI_BASE + "somernd",
				FaultResponse.class, true);
		assertNotNull(fault);
		assertNotNull(fault.getFault());
		String value = read(fault.getFault(), mockMvc);
		assertThat(value, containsString(NOT_FOUND));
	}
	@Test
	public void testMakeRandom() {
		JsonNode rsp = mockGet(mockMvc, URI_RANDOM.replace(URI_BUCKET, BUCKET_ID), JsonNode.class, true);
		assertNotNull(rsp);
	}
	@Test
	public void testUpdate() throws Exception {
		String key = create(mockMvc).getSlug();
		String uKey = update(key, JSON_2, mockMvc);
		assertTrue(StringUtils.equals(key, uKey));
		String uValue = read(uKey, mockMvc);
		assertTrue(StringUtils.equals(uValue, JSON_2));
	}
	@Test
	public void testRead() throws Exception {
		String key = create(mockMvc).getSlug();
		String value = read(key, mockMvc);
		assertTrue(StringUtils.equals(value, JSON_1));
	}
	@Test
	public void testDelete() throws Exception {
		String key = create(mockMvc).getSlug();
		String dKey = del(key, mockMvc);
		assertTrue(StringUtils.equals(key, dKey));
		String read = read(dKey, mockMvc, false);
		assertTrue(StringUtils.equals(read, SLUG_404));
	}
	@Test
	public void testStats() throws Exception {
		CacheStatsResponse stats = mockGet(mockMvc, URI_STATS.replace(URI_BUCKET, BUCKET_ID), CacheStatsResponse.class,
				true);
		assertEquals(stats.getMax(), Long.valueOf(50));
	}
}
