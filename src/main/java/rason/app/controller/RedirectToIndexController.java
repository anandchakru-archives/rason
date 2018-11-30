package rason.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectToIndexController {
	// Match everything without a suffix (so not a static resource)
	@RequestMapping(value = "/**/{[path:[^\\.]*}")
	public String redirect() {
		return "forward:/index.html";
	}
}