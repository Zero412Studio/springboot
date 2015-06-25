package example.springsamples.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 */
@RestController
public class HomeController {

	@RequestMapping("/test")
	public String getHomePage() {
		return "index";
	}
}
