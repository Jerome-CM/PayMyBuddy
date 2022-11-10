package fr.cm.paymybuddy.Servlet;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	  @RequestMapping("/*")
	  @RolesAllowed("USER")
	  public String getUser() {
		  return "Hello User";
	  }
	
	  @RequestMapping("/admin")
	  @RolesAllowed("ADMIN")
	  public String getAdmin() {
		  return "Hello Chef";
	  }
	
	  
}
