package rason.app.rest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;
import static rason.app.TestUtil.JSON_1;
import static rason.app.TestUtil.JSON_2;
import static rason.app.TestUtil.JSON_INVALID;
import static rason.app.TestUtil.SLUG_404;
import static rason.app.TestUtil.create;
import static rason.app.TestUtil.del;
import static rason.app.TestUtil.read;
import static rason.app.TestUtil.update;
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
import rason.app.config.RasonConfig;

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
}
