package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.Controller.JSPDispatcher;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.AccesServiceInt;
import fr.cm.paymybuddy.Utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class AccessService implements AccesServiceInt {

	private static final Logger logger = LogManager.getLogger(AccessService.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	Utility utility;

    @Override
    public RedirectView login(HttpServletRequest request) {

		String mail = request.getParameter("mail");
		String mdp = request.getParameter("password");

		logger.info("Data from login - mail : {} password : {}", mail, mdp );

		try{
			if(utility.isUserExist((mail))){

				HttpSession session = request.getSession();
				session.setAttribute("mail", mail);

				return new RedirectView("/transfert");
			} else {
				User user = new User();
				user.setMail(mail);
				user.setDateCreation(new Date());
				user.setPassword(passwordEncoder.encode(mdp));

				userRepository.save(user);

				HttpSession session = request.getSession();
				session.setAttribute("mail", mail);

				return new RedirectView("/register");
            }
		} catch(Exception e){
			logger.error("Impossible to save a new user : {}", e.getMessage());
			return new RedirectView("/login?error=reload");
		}

    }

    @Override
    public RedirectView logout(HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.invalidate();

		return new RedirectView("/home?status=disconnected");
    }
}
