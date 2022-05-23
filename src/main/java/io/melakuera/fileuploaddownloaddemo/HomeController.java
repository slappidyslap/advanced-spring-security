package io.melakuera.fileuploaddownloaddemo;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	String getHomePage(OAuth2AuthenticationToken token) {
		System.out.println(token.getPrincipal().toString());
		return "home";
	}
}
