package rason.app.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;
import static rason.app.util.RasonConstant.BEAN_CACHE;
import java.io.IOException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import rason.app.config.RasonConfig;
import rason.app.model.JsonVal;
import rason.app.model.StringKey;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import({ RasonConfig.class })
public class SluggerServiceTest {
	@MockBean
	private SluggerService slugger;
	@Autowired
	private RasonSettings settings;
	@Autowired
	@Qualifier(BEAN_CACHE)
	private CacheService cacheService;
	private final String SLUG = "3p14s5";

	@BeforeClass
	public static void profile() {
		System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, "junit");
	}
	@Before
	public void setup() throws IOException {
		when(slugger.gen()).thenReturn(new StringKey(SLUG));
		when(slugger.slug(anyString())).thenCallRealMethod();
		when(slugger.slug(isNull())).thenCallRealMethod();
		ReflectionTestUtils.setField(slugger, "settings", settings);
		ReflectionTestUtils.setField(slugger, BEAN_CACHE, cacheService.cache());
		cacheService.cache().put(new StringKey(SLUG), new JsonVal(new ObjectMapper().readTree("{}")));
	}
	@Test
	public void testSlug() {
		StringKey sKey = slugger.slug(null);
		assertEquals(SLUG, sKey.getSlug());
		//testing max retry
		assertEquals(SLUG, slugger.slug(sKey.getSlug()).getSlug());
	}
}
