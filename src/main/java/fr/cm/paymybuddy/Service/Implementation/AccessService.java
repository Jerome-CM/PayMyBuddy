package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.AccessServiceInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;


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
	public RedirectView register(HttpServletRequest request) {
		logger.info("--- Method register ---");

		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");

		if(!firstname.isEmpty() && !lastname.isEmpty() && !mail.isEmpty() && !password.isEmpty()){

			logger.info("Complete registration - firstname : {} lastname : {} for mail : {}", firstname, lastname, mail );

			User userFind = userRepository.findByMail(mail);
			logger.info("User infos before registration : {}", userFind);
			if(userFind == null){
				User user = new User();

				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setMail(mail);
				user.setPassword(passwordEncoder.encode(password));

				try{
					userRepository.save(user);
					logger.info("New User create {}", user);
					HttpSession session = request.getSession();
					session.setAttribute("mail", mail);
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
	/*

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

*/

    @Override
    public RedirectView logout(HttpServletRequest request) {

		logger.info("--- Method logout ---");

		HttpSession session = request.getSession();
		User user = userRepository.findByMail((String)session.getAttribute("mail"));
		session.invalidate();
		/*Cookie[] cookies = request.getCookies();
		String nameCookie = "remember-me";
		if ( cookies != null ) {
			for ( Cookie cookie : cookies ) {
				if ( cookie != null && nameCookie.equals( cookie.getName() ) ) {
					cookie.setMaxAge(0);
				}
			}
		}
        */
		logger.info("User disconnected - {}", user.getMail());
		return new RedirectView("/?status=disconnected");
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
