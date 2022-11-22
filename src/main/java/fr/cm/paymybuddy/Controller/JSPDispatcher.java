package fr.cm.paymybuddy.Controller;

import antlr.ASTNULLType;
import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Implementation.TransactionService;
import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class JSPDispatcher {

	private UserRepository userRepository;

	private TransactionServiceInt transactionService;

	public JSPDispatcher(UserRepository userRepository, TransactionServiceInt transactionService) {
		this.userRepository = userRepository;
		this.transactionService = transactionService;
	}

	@GetMapping("/login")
	public String getLogin(HttpServletRequest request, ModelMap map) {

		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");

			if (statusType.equals("errorPassEmpty")) {
				HttpSession session = request.getSession();
				session.setAttribute("error", "Your password is empty");
			} else if (statusType.equals("errorMailEmpty")) {
				HttpSession session = request.getSession();
				session.setAttribute("error", "Your mail is empty");
			}
		}
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
	public String getTransfert(HttpServletRequest request, ModelMap map) {
		HttpSession session = request.getSession();
		long id = userRepository.findByMail((String) session.getAttribute( "mail" )).getId();
		// List<TransactionDTO> listTransac = transactionService.historyTransaction(id);
		// map.addAttribute("listTransac", listTransac);

		return "transfert";
	}

	@GetMapping("/profile")
	public String getProfile(HttpServletRequest request, ModelMap map) {
		HttpSession session = request.getSession();
		double userBalance = userRepository.findByMail((String) session.getAttribute( "mail" )).getAccountBalance();
		map.addAttribute("userBalance", userBalance);
		return "profile";
	}

	@GetMapping("/addFriend")
	public String getAddFriend(ModelMap map) {

		List<User> listUser = (List<User>) userRepository.findAll();
		map.addAttribute("listUsers", listUser);
		return "addFriend";
	}

	@GetMapping("/contact")
	public String getContact() {
		return "contact";
	}

}