package fr.cm.paymybuddy.Controller;

import antlr.ASTNULLType;
import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.DTO.ProfilDTO;
import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.Model.Transaction;
import fr.cm.paymybuddy.Model.User;

import fr.cm.paymybuddy.Repository.TransactionRepository;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Implementation.TransactionService;
import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JSPDispatcher {

	private static final Logger logger = LogManager.getLogger(JSPDispatcher.class);

	private UserRepository userRepository;

	private TransactionRepository transactionRepository;
	private RelationServiceInt relationService;
	private TransactionServiceInt transactionService;
	private OtherServiceInt otherService;

	@Autowired
	ModelMapper modelMapper;


	public JSPDispatcher(UserRepository userRepository, TransactionServiceInt transactionService, RelationServiceInt relationService,OtherServiceInt otherService,TransactionRepository transactionRepository) {
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
		this.transactionService = transactionService;
		this.relationService = relationService;
		this.otherService = otherService;
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
				session.setAttribute("error", "You don't have choose a friend");
			}  else if (statusType.equals("success")) {
				session.setAttribute("error", "Friend recorded with success !");
			}
		}

		User me = userRepository.findByMail((String) session.getAttribute( "mail" ));
		map.addAttribute("listMyFriends", relationService.getListOfMyFriends(me.getMail()));

		int nbrTransactionPerPage = 3;
		int limitStart = 0;
		int limitEnd;
		int totalNbrPage = (int)Math.ceil(transactionRepository.findAllByUserId(me.getId()).size() / nbrTransactionPerPage);


		Integer requestPage = request.getParameter("page") != null?Integer.parseInt(request.getParameter("page")):0;
		if(session.getAttribute("page") == null || Integer.parseInt(request.getParameter("page")) <= 1){
			session.setAttribute("page", 1);
		} else {
			if(Integer.parseInt(request.getParameter("page")) >= totalNbrPage){

			} else {
				session.setAttribute("page", request.getParameter("page"));
			}

		}
		logger.info("pageRequest sended {}", requestPage);
		logger.info("total page {}", totalNbrPage);
		if( requestPage == 0 || requestPage > totalNbrPage ) {
			limitEnd = limitStart + nbrTransactionPerPage;
		} else {
			limitStart = (requestPage * nbrTransactionPerPage) - nbrTransactionPerPage;
			limitEnd = (requestPage * nbrTransactionPerPage);
		}
		logger.info("Limit {},{}", limitStart, limitEnd);
		map.addAttribute("nbrPages", totalNbrPage);
		map.addAttribute("listTransac", transactionService.historyTransaction(me.getId(),limitStart,limitEnd));

		return "transfert";
	}

	@GetMapping("/profile")
	public String getProfile(HttpServletRequest request, ModelMap map) {
		String url = request.getRequestURI();
		List<String> accessPath = otherService.accessPath(url);
		map.addAttribute("accessPath", accessPath);

		HttpSession session = request.getSession();
		User me = userRepository.findByMail((String) session.getAttribute( "mail" ));

		ProfilDTO profileDTO = new ProfilDTO();
		profileDTO = modelMapper.map(me, ProfilDTO.class);

		map.addAttribute("userTest", relationService.testGetProfileDTO(me));
		map.addAttribute("userBalance", userRepository.findByMail(me.getMail()).getAccountBalance());
		map.addAttribute("listMyFriends", relationService.getListOfMyFriends(me.getMail()));
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
				session.setAttribute("error", "You don't have choose a friend");
			} else if (statusType.equals("errorAlreadyFriend")) {
				session.setAttribute("error", "You have already a relation with this friend");
			} else if (statusType.equals("success")) {
				session.setAttribute("error", "Friend recorded with success !");
			}
		}

		String myMail = (String) session.getAttribute( "mail" );

		map.addAttribute("listUsers", relationService.getAllUserWithoutMe(myMail));
		map.addAttribute("listMyFriends", relationService.getListOfMyFriends(myMail));

		return "addFriend";
	}

	@GetMapping("/contact")
	public String getContact(HttpServletRequest request, ModelMap map) {
		String url = (String)request.getRequestURI();
		List<String> accessPath = otherService.accessPath(url);
		map.addAttribute("accessPath", accessPath);

		return "contact";
	}

}