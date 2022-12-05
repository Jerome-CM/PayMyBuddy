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

    @Override
    public RedirectView login(HttpServletRequest request) {

		logger.info("--- Method login ---");

		String mail = request.getParameter("mail");
		String mdp = request.getParameter("password");

		logger.info("Data from login - Mail : {} Password : {}", mail, mdp );


		if(this.isUserExist((mail))){

			HttpSession session = request.getSession();
			session.setAttribute("mail", mail);
			logger.info("User connected - Mail : {} ", mail );
			return new RedirectView("/transfert");
		} else {
			if (mdp.isEmpty()) {
				return new RedirectView("/login?status=errorPassEmpty");
			} else if (mail.isEmpty()) {
				return new RedirectView("/login?status=errorMailEmpty");
			}else {

				User user = new User();
				user.setMail(mail);
				user.setPassword(passwordEncoder.encode(mdp));

				try{
					user = userRepository.save(user);
					logger.info("New User create {}", user);
					HttpSession session = request.getSession();
					session.setAttribute("mail", mail);

					return new RedirectView("/register");

				}  catch(Exception e){
					logger.error("Impossible to save a new user : {}", e.getMessage());
					return new RedirectView("/login?error=reload");
				}
			}
		}
    }

    @Override
    public RedirectView logout(HttpServletRequest request) {

		logger.info("--- Method logout ---");

		HttpSession session = request.getSession();
		User user = userRepository.findByMail((String) session.getAttribute("mail"));
		session.invalidate();
		logger.info("User disconnected - {}", user.getMail());
		return new RedirectView("/home?status=disconnected");
    }

	public boolean isUserExist(String mail){

		logger.info("--- Method isUserExist ---");

		User user = userRepository.findByMail(mail);

		if(user == null){
			logger.info("User not found during connection request");
			return false;
		} else {
			logger.info("User finded during connection request : {}", user);
			return true;
		}
	}

}
