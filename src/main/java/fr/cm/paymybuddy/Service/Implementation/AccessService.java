package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.Controller.DemoController;
import fr.cm.paymybuddy.DTO.UserDTO;
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

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class AccessService implements AccesServiceInt {

	private static final Logger logger = LogManager.getLogger(AccessService.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	DemoController controller;

	@Autowired
	PasswordEncoder passwordEncoder;
    @Override
    public void register(HttpServletRequest request) {

    }

    @Override
    public String login(HttpServletRequest request) {

		String mail = request.getParameter("mail");
		String mdp = request.getParameter("password");

		logger.info("Data from login - mail : {} password : {}", mail, mdp );
		User user = new User();
		user.setMail(mail);
		user.setDateCreation(new Date());
		user.setPassword(passwordEncoder.encode(mdp));

		try{
			if(Utility.isUserExist(userRepository.findByMail(user.getMail()))){
				userRepository.save(user);
				return "";//TODO la jsp home.jsp
			} else {
                return "";//TODO la jsp login avec l'erreur en paramètre GET
            }
		} catch(Exception e){
			logger.error("Impossible to save a new user : {}", e.getMessage());
            return "Error : " + e.getMessage();
		}

    }

    @Override
    public boolean logout(HttpServletRequest request) {
        return false;
    }
}
