package rason.app.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;
import static rason.app.TestUtil.BUCKET_ID;
import static rason.app.TestUtil.mockGet;
import static rason.app.util.RasonConstant.HB_PREFIX;
import static rason.app.util.RasonConstant.URI_HB;
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
import rason.app.config.RasonConfig;
import rason.app.model.CacheStatsResponse;
import rason.app.model.StrResponse;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import({ RasonConfig.class })
public class MetaControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void profile() {
		System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, "junit");
	}
	@Test
	public void testHb() throws Exception {
		StrResponse hb = mockGet(mockMvc, URI_HB, StrResponse.class, true);
		assertTrue("Invalid HeartBeat Response", StringUtils.startsWith(hb.getPayload(), HB_PREFIX));
	}
	@Test
	public void testStats() throws Exception {
		CacheStatsResponse stats = mockGet(mockMvc, URI_STATS.replace("{bucketId}", BUCKET_ID),
				CacheStatsResponse.class, true);
		assertEquals(stats.getMax(), Long.valueOf(50));
	}
}
