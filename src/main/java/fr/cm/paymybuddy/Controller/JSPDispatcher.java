package fr.cm.paymybuddy.Controller;


import fr.cm.paymybuddy.Model.Transaction;
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
			/* Display error message on the page with session status */
			if (statusType.equals("errorPassEmpty")) {
				session.setAttribute("notification", "Your password is empty");
			} else if (statusType.equals("errorMailEmpty")) {
				session.setAttribute("notification", "Your mail is empty");
			} else if (statusType.equals("errorfoundUser")) {
				session.setAttribute("notification", "Sorry, you don't have found, please login first");
			} else if (statusType.equals("disconnected")) {
				session.setAttribute("notification", "You have been logout with success");
			}
		}
		return "login";
	}

	@GetMapping("/register")
	public String getRegister(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");
			/* Display error message on the page with session status */
			if (statusType.equals("errorNewUser")) {
				session.setAttribute("notification", "Save yours informations is impossible");
			} else if (statusType.equals("errorInputEmpty")){
				session.setAttribute("notification", "Please complete all fields");
			} else if (statusType.equals("errorExistMail")){
			session.setAttribute("notification", "This mail exist already");
		}
		}
		return "register";
	}


	@GetMapping("/")
	public String getHome(HttpServletRequest request, ModelMap map) {
		/* Display on the screen the breadcrumb trail */ 
		List<String> accessPath = otherService.accessPath(request.getRequestURI());
		map.addAttribute("accessPath", accessPath);

		return "home";
	}

	@GetMapping("/transfert")
	public String getTransfert(HttpServletRequest request, ModelMap map) {
		/* Display on the screen the breadcrumb trail */
		List<String> accessPath = otherService.accessPath((String)request.getRequestURI());
		map.addAttribute("accessPath", accessPath);

		HttpSession session = request.getSession();
		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");
			/* Display error message on the page with session status */
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


		// Pagination config
		int nbrTransactionPerPage = 3; // Never 0 !
		int limitStart = 0;
		int limitEnd;
		int totalNbrPage = (int)Math.ceil(transactionRepository.findAllByUserId(me.getId()).size() / nbrTransactionPerPage);
		int requestPage = 0;

		// Exceptions
		if (request.getParameter("page") != null && Integer.parseInt(request.getParameter("page")) > totalNbrPage){
			requestPage = totalNbrPage;
			session.setAttribute("page", totalNbrPage);
		} else if(request.getParameter("page") != null && Integer.parseInt(request.getParameter("page")) < 0){
			requestPage = 0;
			session.setAttribute("page", 1);
		} else if (request.getParameter("page") != null && session.getAttribute("page") != null) {
			requestPage = Integer.parseInt(request.getParameter("page"));
			session.setAttribute("page", requestPage);
		} else {
			// Initialize pagination
			requestPage = 0;
			session.setAttribute("page", 1);
		}

		// Param Limit SQL
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
			/* Display error message on the page with session status */
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

		/* Display on the screen the breadcrumb trail */
		List<String> accessPath = otherService.accessPath((String)request.getRequestURI());
		map.addAttribute("accessPath", accessPath);

		/* Get user infos */
		User me = userRepository.findByMail((String) session.getAttribute( "mail" ));
		map.addAttribute("user", relationService.getProfileDTO(me));

		return "profile";
	}

	@GetMapping("/addFriend")
	public String getAddFriend(HttpServletRequest request, ModelMap map) {
		/* Display on the screen the breadcrumb trail */
		List<String> accessPath = otherService.accessPath((String)request.getRequestURI());
		map.addAttribute("accessPath", accessPath);

		HttpSession session = request.getSession();

		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");
			/* Display error message on the page with session status */
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
		/* Display on the screen the breadcrumb trail */
		List<String> accessPath = otherService.accessPath((String)request.getRequestURI());
		map.addAttribute("accessPath", accessPath);

		HttpSession session = request.getSession();
		if(request.getParameter("status") != null) {
			String statusType = request.getParameter("status");
			/* Display error message on the page with session status */
			if (statusType.equals("sentMessage")) {
				session.setAttribute("notification", "Your message as been send");
			}}

		return "contact";
	}

	@GetMapping("/admin/manage")
	public String getManage(HttpServletRequest request, ModelMap map) {
		/* Display on the screen the breadcrumb trail */
		List<String> accessPath = otherService.accessPath((String)request.getRequestURI());
		map.addAttribute("accessPath", accessPath);

		/* Count all users */
		List<User> userList = (List<User>) userRepository.findAll();
		/* Count all transactions */
		List<Transaction> transacList = (List<Transaction>) transactionRepository.findAll();

		double totalMoney = 0;
		double totalWallet = 0;

		/* Count all money sent with the app */
		for(Transaction transa : transacList){
			if(!transa.getUser().equals(transa.getUserFriend())){
				totalMoney += transa.getAmount();
			}
		}

		/* Count all money in the wallets of app */
		for(User user : userList){
			totalWallet += user.getAccountBalance();
		}

		map.addAttribute("nbrUsers", userList.size());
		map.addAttribute("nbrTransactions", transacList.size());
		map.addAttribute("totalMoney", totalMoney);
		map.addAttribute("moneyInWallet", totalWallet);

		return "manage";
	}

	@GetMapping("/403")
	public String getAccessDenied() {

		return "accessForbidden";
	}

}