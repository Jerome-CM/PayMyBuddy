package fr.cm.paymybuddy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JSPDispatcher {

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}

	@GetMapping("/transfert")
	public String getTransfert() {
		return "transaction";
	}

	@GetMapping("/profile")
	public String getProfile() {
		return "profile";
	}

	@GetMapping("/addFriend")
	public String getAddFriend() {
		return "addFriend";
	}

	@GetMapping("/contact")
	public String getContact() {
		return "contact";
	}

}