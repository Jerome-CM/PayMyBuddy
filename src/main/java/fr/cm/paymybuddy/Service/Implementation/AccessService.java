package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.AccessServiceInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
public class AccessService implements AccessServiceInt {

	private static final Logger logger = LogManager.getLogger(AccessService.class);

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	public AccessService(UserRepository userRepository,PasswordEncoder passwordEncoder){
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}

	/**
	 * Create a new user in DB
	 * @param request
	 * @return
	 */
	@Override
	public RedirectView register(HttpServletRequest request) {
		logger.info("--- Method register ---");
		/* Get parameter from register jsp */
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");

		if(!firstname.isEmpty() && !lastname.isEmpty() && !mail.isEmpty() && !password.isEmpty()){

			logger.info("Complete registration - firstname : {} lastname : {} for mail : {}", firstname, lastname, mail );
			/* Find user with mail in the request */
			User userFind = userRepository.findByMail(mail);
			logger.info("User infos before registration : {}", userFind);
			if(userFind == null){
				/* Create a user if not exist */
				User user = new User();

				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setMail(mail);
				user.setPassword(passwordEncoder.encode(password));

				try{
					/* Insert it in DB  */
					userRepository.save(user);
					logger.info("New User create {}", user);
					return new RedirectView("/transfert?status=successRegister");
				} catch(Exception e){
					logger.error("Impossible to register the new user : {}", e.getMessage());
					return new RedirectView("/register?status=errorNewUser");
				}
			} else {
				logger.error("Mail exist already");
				return new RedirectView("/register?status=errorExistMail");
			}
		} else {
			return new RedirectView("/register?status=errorInputEmpty");
		}
	}

	@Override
	public RedirectView logout(HttpServletRequest request) {

		logger.info("--- Method logout ---");

		HttpSession session = request.getSession();
		User user = userRepository.findByMail((String)session.getAttribute("mail"));
		session.invalidate();
		logger.info("User disconnected - {}", user.getMail());
		return new RedirectView("/?status=disconnected");
	}

	/**
	 * Verifying if the mail exist in the DB
	 * @param mail
	 * @return boolean
	 */
	public boolean isUserExist(String mail){

		logger.info("--- Method isUserExist ---");

		User user = userRepository.findByMail(mail);

		if(user == null){
			logger.info("User not found during connection request");
			return false;
		} else {
			logger.info("User found during connection request : {}", user);
			return true;
		}
	}

}
