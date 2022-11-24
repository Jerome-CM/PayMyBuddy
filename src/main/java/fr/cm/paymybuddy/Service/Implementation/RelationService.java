package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.AccesServiceInt;
import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import fr.cm.paymybuddy.Utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
public class RelationService implements RelationServiceInt {

    private static final Logger logger = LogManager.getLogger(RelationService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccesServiceInt accesService;

    @Autowired
    ModelMapper modelMapper;

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
            user.setId(accesService.idUser(mail));

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

    String mailFriend = request.getParameter("mail");
    String myMail = request.getParameter("mail_hidden");

        if(mailFriend.equals("choose")) {
            return new RedirectView("/addFriend?status=errorChooseEmpty");
        } else if(isItMyFriend(myMail,mailFriend)){
            return new RedirectView("/addFriend?status=errorAlreadyFriend");
        } else {
            User userFriend = userRepository.findByMail(mailFriend);
            User me = userRepository.findByMail(myMail);

            logger.info("Friend finded with {} : {}", mailFriend, userFriend);

            List<User> myFriends = me.getFriends();
            myFriends.add(userFriend);
            logger.info("New list friend : {}", myFriends);
            me.setFriends(myFriends);
            try {
                userRepository.save(me);
                return new RedirectView("/addFriend?status=success");
            } catch (Exception e) {
                logger.error("Impossible to save a user with this friend : {} Error : {}", userFriend, e.getMessage());
            }
        }

        return new RedirectView("/addFriend?status=error");
    }

    @Override
    public RedirectView deleteFriend(FriendDTO friendDTO) {
        return new RedirectView("/");
    }

    @Override
    public boolean isItMyFriend(String myMail, String mailFriend){

        logger.info("Mail sended : me {}, mail friend {}",myMail, mailFriend);

        List<User> myfriends = userRepository.getMyFriends(userRepository.findByMail(myMail).getId());
        logger.info("My friends finded : {}", myfriends);

        for(User user : myfriends){
            if(user.getMail().equals(mailFriend)){
                return true;
            }
        }
        return false;
    }

    public List<FriendDTO> getListOfMyFriends(String mail){

        long idMail = userRepository.findByMail(mail).getId();
        logger.info("Mail sended : {}, id finded {}",mail, idMail);

        List<User> listFriends = userRepository.getMyFriends(idMail);
        logger.info("My friends finded : {}", listFriends);
        
        List<FriendDTO> listFriendsDTO = new ArrayList<>();
        for(User friend : listFriends){
            FriendDTO friendDTO = modelMapper.map(friend, FriendDTO.class);
            listFriendsDTO.add(friendDTO);
        }
        logger.info("return : {}", listFriendsDTO);
        return listFriendsDTO;
    }

    public List<User> getAllUserWithoutMe(String myMail){
        List<User> listUser = (List<User>) userRepository.findAll();

        for ( User user : listUser){
            if(user.getMail().equals(myMail)){
                listUser.remove(listUser.indexOf(user));
            }
        }
        return listUser;
    }
}
