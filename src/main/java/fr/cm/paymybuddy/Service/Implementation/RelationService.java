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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    PasswordEncoder passwordEncoder;

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

        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String mail = request.getParameter("mail_hidden");

        User me = userRepository.findByMail(mail);

        if(password.equals(confirmPassword)){
            me.setPassword(passwordEncoder.encode(password));

            try{
                userRepository.save(me);
                return new RedirectView("/profile?status=successChangePassword");
            } catch (Exception e) {
                return new RedirectView("/profile?status=errorSaveNewPassword");
            }
        } else {
            return new RedirectView("/profile?status=errorPasswordNotSame");
        }


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
    public RedirectView deleteFriend(HttpServletRequest request) {

        String myToDelete = request.getParameter("friend");
        HttpSession session = request.getSession();
        User me = userRepository.findByMail((String)session.getAttribute("mail"));
        List<User> myFriendList = userRepository.getMyFriends(me.getId());

        List<User> myNewFriendList = new ArrayList<>();
        for(User user : myFriendList){
            if(!user.getMail().equals(myToDelete)){
                myNewFriendList.add(user);
            }
        }

        me.setFriends(myNewFriendList);
        try{
            userRepository.save(me);
            return new RedirectView("/profile?status=successRemove");
        } catch (Exception e) {
            return new RedirectView("/profile?status=errorRemove");
        }

    }

    @Override
    public boolean isItMyFriend(String myMail, String mailFriend){

        logger.info("Mail friend {}",myMail, mailFriend);

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

        List<User> listFriends = userRepository.getMyFriends(idMail);

        List<FriendDTO> listFriendsDTO = new ArrayList<>();
        for(User friend : listFriends){
            FriendDTO friendDTO = modelMapper.map(friend, FriendDTO.class);
            listFriendsDTO.add(friendDTO);
        }
        logger.info("List of my FriendDTO : {}", listFriendsDTO);
        return listFriendsDTO;
    }

    public List<FriendDTO> getAllUserWithoutMe(String myMail){
        List<User> listUser = (List<User>) userRepository.findAll();
        List<FriendDTO> listUsersDTO = new ArrayList<>();

        for ( User user : listUser){

            if(!user.getMail().equals(myMail)){
                FriendDTO friendDTO = modelMapper.map(user, FriendDTO.class);
                listUsersDTO.add(friendDTO);
            }
        }
        logger.info("List of all usersDTO without me : {}", listUsersDTO);
        return listUsersDTO;
    }
}
