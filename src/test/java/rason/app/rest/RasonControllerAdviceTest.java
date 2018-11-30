package rason.app.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;
import static rason.app.TestUtil.JSON_INVALID;
import static rason.app.TestUtil.create;
import static rason.app.TestUtil.mockGet;
import static rason.app.TestUtil.to;
import static rason.app.util.RasonConstant.URI_API;
import static rason.app.util.RasonConstant.URI_BASE;
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
import rason.app.model.FaultResponse;
import rason.app.model.StringKey;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import({ RasonConfig.class })
public class RasonControllerAdviceTest {
	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void profile() {
		System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, "junit");
	}
	@Test
	public void testHandleRasonException() {
		FaultResponse rsp = to(mockGet(mockMvc, URI_API + URI_BASE + "noslug", JsonNode.class, true),
				FaultResponse.class);
		assertNotNull(rsp);
		assertNotNull(rsp.getFault());
	}
	@Test
	public void testHandleHttpMessageConversionException() {
		StringKey create = create(mockMvc, JSON_INVALID);
		assertNull(create);
	}
	@Test
	public void testHandleServletException() {
		FaultResponse rsp = to(mockGet(mockMvc, URI_API + URI_BASE, JsonNode.class, true), FaultResponse.class);
		assertNotNull(rsp);
		assertNotNull(rsp.getFault());
	}
}
