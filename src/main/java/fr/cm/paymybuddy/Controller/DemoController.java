package fr.cm.paymybuddy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}

}