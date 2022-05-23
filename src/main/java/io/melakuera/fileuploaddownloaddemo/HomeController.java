package io.melakuera.fileuploaddownloaddemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/user")
	String getUserPage() {
		return "Hello user!";
	}

	@GetMapping("/admin")
	String getAdminPage() {
		return "Hello admin!";
	}

	@GetMapping("/anonymous")
	String getAnonymousPage() {
		return "Hello anonymous";
	}
}
