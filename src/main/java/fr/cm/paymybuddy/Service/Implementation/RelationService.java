package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import fr.cm.paymybuddy.Utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.Date;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
public class RelationService implements RelationServiceInt {

    private static final Logger logger = LogManager.getLogger(RelationService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utility utility;

    @Override
    public RedirectView register(HttpServletRequest request) {

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String mail = request.getParameter("mail");

        logger.info("Data from login - firstname : {} lastname : {} mail : {}", firstname, lastname, mail );

        User user = userRepository.findByMail(mail);

        if(!user.getPassword().isEmpty()){

            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setDateModification(new Date());
            user.setId(utility.idUser(mail));

            try{
                userRepository.save(user);
                return new RedirectView("/transfert?status=success");

            } catch(Exception e){
                logger.error("Impossible to register the news informations : {}", e.getMessage());
                return new RedirectView("/profile?status=errorUpdateUser");
            }
        } else {
            logger.error("User not finded for update");
            return new RedirectView("/register?status=errorFindedUser");
        }
    }

    @Override
    public RedirectView modifyUserInfos(HttpServletRequest request) {

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String mail = request.getParameter("mail");
        String mailHidden = request.getParameter("mail_hidden");

        logger.info("Data from form - firstname : {} lastname : {} new mail : {}, old mail : {}", firstname, lastname, mail, mailHidden );

        User user = userRepository.findByMail(mailHidden);
        logger.info("Old values of user {}", user);

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setLastname(mail);
        user.setDateModification(new Date());

        try{

            userRepository.save(user);
            logger.info("Infos user update : {}", user );

            return new RedirectView("/profile?status=success&modifInfos=yes");

        } catch(Exception e){
            logger.error("Impossible to register the news informations : {}", e.getMessage());
            return new RedirectView("/profile?status=errorUpdateInformations");
        }
    }

    @Override
    public RedirectView modifyUserPassword(HttpServletRequest request) {
        return new RedirectView("/");
    }

    @Override
    public RedirectView addFriend(HttpServletRequest request) {

        long myId = Long.parseLong(request.getParameter("myId"));
        User userFriend = userRepository.findByMail(request.getParameter("mail"));

        logger.info("Friend finded with {} : {}", request.getParameter("mail"), userFriend);

        try{
            logger.info("Try save : me : {}, myFriend : {}", myId, userFriend.getId());
            userRepository.addFriend(myId, userFriend.getId());
            logger.info("Relation saved between {} and {}", myId, userFriend.getId());
            return new RedirectView("/addFriend?status=success");
        } catch (Exception e) {
            logger.error("Error : {}", e.getMessage());
            return new RedirectView("/addFriend?status=error");
        }
    }

    @Override
    public RedirectView deleteFriend(FriendDTO friendDTO) {
        return new RedirectView("/");
    }

    @Override
    public boolean isItMyFriend(String mail) {
        return false;
    }
}
