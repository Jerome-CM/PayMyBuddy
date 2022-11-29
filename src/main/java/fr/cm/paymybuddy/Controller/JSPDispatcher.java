package fr.cm.paymybuddy.Controller;


import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.TransactionRepository;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class JSPDispatcher {

	private static final Logger logger = LogManager.getLogger(JSPDispatcher.class);

	private UserRepository userRepository;
	private TransactionRepository transactionRepository;
	private RelationServiceInt relationService;
	private TransactionServiceInt transactionService;
	private OtherServiceInt otherService;

	public JSPDispatcher(UserRepository userRepository, TransactionServiceInt transactionService, RelationServiceInt relationService,OtherServiceInt otherService,TransactionRepository transactionRepository) {
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
		this.transactionService = transactionService;
		this.relationService = relationService;
		this.otherService = otherService;
	}

	@GetMapping("/login")
	public String getLogin(HttpServletRequest request, ModelMap map) {
		HttpSession session = request.getSession();
		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");

			if (statusType.equals("errorPassEmpty")) {
				session.setAttribute("notification", "Your password is empty");
			} else if (statusType.equals("errorMailEmpty")) {
				session.setAttribute("notification", "Your mail is empty");
			} else if (statusType.equals("errorFindedUser")) {
				session.setAttribute("notification", "Sorry, you don't have finded, please login first");
			}
		}
		return "login";
	}

	@GetMapping("/register")
	public String getRegister(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");

			if (statusType.equals("errorUpdateUser")) {
				session.setAttribute("notification", "Update yours informations is impossible");
			} else if (statusType.equals("errorInputEmpty")){
				session.setAttribute("notification", "Please complete all fields");
			}
		}
		return "register";
	}


	@GetMapping("/home")
	public String getHome(HttpServletRequest request, ModelMap map) {
		String url = (String)request.getRequestURI();
		List<String> accessPath = otherService.accessPath(url);
		map.addAttribute("accessPath", accessPath);
		return "home";
	}

	@GetMapping("/transfert")
	public String getTransfert(HttpServletRequest request, ModelMap map) {
		String url = request.getRequestURI();
		List<String> accessPath = otherService.accessPath(url);
		map.addAttribute("accessPath", accessPath);

		HttpSession session = request.getSession();
		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");

			if (statusType.equals("errorChooseEmpty")) {
				session.setAttribute("notification", "You don't have choose a friend");
			}  else if (statusType.equals("errorLittleAmount")) {
				session.setAttribute("notification", "Please, choose an amount better than 1€");
			} else if (statusType.equals("successTransfer")) {
				session.setAttribute("notification", "Transfer success : "+ request.getParameter("amount") +"€ to "+ request.getParameter("friend"));
			} else if (statusType.equals("errorTransfert")) {
				session.setAttribute("notification", "This transfer is impossible");
			} else if (statusType.equals("errorNotMoney")) {
				session.setAttribute("notification", "You don't have enought money for this transfer amount");
			} else if (statusType.equals("successRegister")) {
				session.setAttribute("notification", "Your registration is full, welcome to PayMyBuddy !");
			}
		}

		User me = userRepository.findByMail((String) session.getAttribute( "mail" ));
		map.addAttribute("userDTO", relationService.getProfileDTO(me));

		int nbrTransactionPerPage = 3;
		int limitStart = 0;
		int limitEnd;
		int totalNbrPage = (int)Math.ceil(transactionRepository.findAllByUserId(me.getId()).size() / nbrTransactionPerPage);

		Integer requestPage = 0;
		// Initialize pagination
		if(request.getParameter("page") == null && session.getAttribute("page") == null){
			session.setAttribute("page", 1);
			requestPage = 0;
		// Current page
		} else if (request.getParameter("page") != null && session.getAttribute("page") != null){
			requestPage = Integer.valueOf(request.getParameter("page"));
			session.setAttribute("page", requestPage);
		// Exception
		} else if (request.getParameter("page") != null && Integer.valueOf(request.getParameter("page")) > totalNbrPage){
			requestPage = totalNbrPage;
			session.setAttribute("page", totalNbrPage);
		} else if(request.getParameter("page") != null && Integer.valueOf(request.getParameter("page")) < 0){
			requestPage = 0;
			session.setAttribute("page", 1);
		} else {
			requestPage = 0;
			session.setAttribute("page", 1);
		}

		if( requestPage == 0) {
			limitEnd = limitStart + nbrTransactionPerPage;
		} else {
			limitStart = (requestPage * nbrTransactionPerPage) - nbrTransactionPerPage;
			limitEnd = nbrTransactionPerPage;
		}
		logger.info("Limit {},{}", limitStart, limitEnd);
		map.addAttribute("nbrPages", totalNbrPage);
		map.addAttribute("listTransacDTO", transactionService.historyTransaction(me.getId(),limitStart,limitEnd));

		return "transfert";
	}

	@GetMapping("/profile")
	public String getProfile(HttpServletRequest request, ModelMap map) {
		HttpSession session = request.getSession();

		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");

			if (statusType.equals("errorUpdateInformations")) {
				session.setAttribute("notification", "Update yours informations is impossible");
			} else if (statusType.equals("errorSaveNewPassword")) {
				session.setAttribute("notification", "Save your new passwword is impossible");
			} else if (statusType.equals("errorPasswordNotSame")) {
				session.setAttribute("notification", "Yours password isn't same !");
			} else if (statusType.equals("successModifInfos")) {
				session.setAttribute("notification", "Yours informations is saved");
			} else if (statusType.equals("successChangePassword")) {
				session.setAttribute("notification", "New password recorded with success !");
			} else if (statusType.equals("errorRefund")) {
				session.setAttribute("notification", "The refund is impossible");
			} else if (statusType.equals("successRefund")) {
				session.setAttribute("notification", "Your account is reloaded now with "+ request.getParameter("amount") +"€");
			} else if (statusType.equals("successRemove")) {
				session.setAttribute("notification", "This friend is deleted in your friend list");
			}
		}

		String url = request.getRequestURI();
		List<String> accessPath = otherService.accessPath(url);
		map.addAttribute("accessPath", accessPath);


		User me = userRepository.findByMail((String) session.getAttribute( "mail" ));
		map.addAttribute("user", relationService.getProfileDTO(me));

		return "profile";
	}

	@GetMapping("/addFriend")
	public String getAddFriend(HttpServletRequest request, ModelMap map) {
		String url = (String)request.getRequestURI();
		List<String> accessPath = otherService.accessPath(url);
		map.addAttribute("accessPath", accessPath);

		HttpSession session = request.getSession();

		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");

			if (statusType.equals("errorChooseEmpty")) {
				session.setAttribute("notification", "You don't have choose a friend");
			} else if (statusType.equals("errorAlreadyFriend")) {
				session.setAttribute("notification", "You have already a relation with this friend");
			} else if (statusType.equals("success")) {
				session.setAttribute("notification", "Friend recorded with success !");
			}
		}

		User me = userRepository.findByMail((String) session.getAttribute( "mail" ));

		map.addAttribute("listUsers", relationService.getAllUserWithoutMe(me.getMail()));
		map.addAttribute("listMyFriends", relationService.getProfileDTO(me));
		return "addFriend";
	}

	@GetMapping("/contact")
	public String getContact(HttpServletRequest request, ModelMap map) {
		String url = (String)request.getRequestURI();
		List<String> accessPath = otherService.accessPath(url);
		map.addAttribute("accessPath", accessPath);

		HttpSession session = request.getSession();
		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");

			if (statusType.equals("sentMessage")) {
				session.setAttribute("notification", "Your message as been send");
			}}

		return "contact";
	}

}