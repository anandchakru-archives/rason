package rason.app.rest;

import static rason.app.util.RasonConstant.HB_PREFIX;
import static rason.app.util.RasonConstant.URI_BASE;
import static rason.app.util.RasonConstant.URI_HB;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rason.app.model.StrResponse;

@RestController
public class MetaController {
	@GetMapping(value = { URI_BASE, URI_HB }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody StrResponse hb() {
		return new StrResponse(HB_PREFIX + System.currentTimeMillis());
	}
}