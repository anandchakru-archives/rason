package rason.app.rest;

import static org.springframework.core.env.AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;
import static rason.app.TestUtil.bul;
import static rason.app.TestUtil.bum;
import static rason.app.TestUtil.read;
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
public class BulkUploadControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@BeforeClass
	public static void profile() {
		System.setProperty(DEFAULT_PROFILES_PROPERTY_NAME, "junit");
	}
	@Test
	public void testBulkMap() {
		bum(mockMvc);
		bum(mockMvc);
		String one = read("one", mockMvc);
		System.out.println(one);
	}
	@Test
	public void testCreate() {
		bul(mockMvc);
	}
}