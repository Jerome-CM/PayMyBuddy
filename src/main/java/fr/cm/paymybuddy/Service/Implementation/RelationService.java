package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.DTO.ProfilDTO;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.AccessServiceInt;
import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RelationService implements RelationServiceInt {

    private static final Logger logger = LogManager.getLogger(RelationService.class);

    private UserRepository userRepository;
    private AccessServiceInt accesService;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public RelationService(UserRepository userRepository, AccessServiceInt accesService, PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.userRepository=userRepository;
        this.accesService=accesService;
        this.passwordEncoder=passwordEncoder;
        this.modelMapper=modelMapper;
    }


    /**
     *
     * @param request
     * @return a jsp with a status message
     */
    @Override
    public RedirectView modifyUserInfos(HttpServletRequest request) {

        logger.info("--- Method modifyUserInfos ---");

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String mail = request.getParameter("mail");
        String mailHidden = request.getParameter("mail_hidden");

        logger.info("Modify infos request - firstname : {} lastname : {} new mail : {}, old mail : {}", firstname, lastname, mail, mailHidden );

        /* Find user with the old email address */
        User user = userRepository.findByMail(mailHidden);
        logger.info("Old values of user {}", user);

        /* Set the new values */
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setMail(mail);
        user.setDateModification(new Date());

        try{
            userRepository.save(user);
            logger.info("User infos update : {}", user);
            if(!mail.equals(mailHidden)){
                /* If the mail at changed, we insert in the session the new mail */
                HttpSession session = request.getSession();
                session.setAttribute("mail", mail);
            }
            logger.info("Infos user update : {}", user );
            return new RedirectView("/profile?status=successModifInfos");

        } catch(Exception e){
            logger.error("Impossible to register the news informations : {}", e.getMessage());
            return new RedirectView("/profile?status=errorUpdateInformations");
        }
    }

    /**
     *
     * @param request
     * @return a jsp with a status message
     */
    @Override
    public RedirectView modifyUserPassword(HttpServletRequest request) {

        logger.info("--- Method modifyUserPassword ---");

        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String mail = request.getParameter("mail_hidden");

        User me = userRepository.findByMail(mail);

        if(password.equals(confirmPassword)){
            me.setPassword(passwordEncoder.encode(password));

            try{
                userRepository.save(me);
                logger.info("User password update");
                return new RedirectView("/profile?status=successChangePassword");
            } catch (Exception e) {
                return new RedirectView("/profile?status=errorSaveNewPassword");
            }
        } else {
            return new RedirectView("/profile?status=errorPasswordNotSame");
        }


    }

    /**
     *
     * @param request
     * @return a jsp with a status message
     */
    @Override
    public RedirectView addFriend(HttpServletRequest request) {

    logger.info("--- Method addFriend ---");

    String mailFriend = request.getParameter("mail");
    String myMail = request.getParameter("mail_hidden");

        if(mailFriend.equals("choose")) {
            logger.info("errorChooseEmpty");
            return new RedirectView("/addFriend?status=errorChooseEmpty");
        } else if(isItMyFriend(myMail,mailFriend)){
            logger.info("errorAlreadyFriend");
            return new RedirectView("/addFriend?status=errorAlreadyFriend");
        } else {
            User userFriend = userRepository.findByMail(mailFriend);
            User me = userRepository.findByMail(myMail);

            logger.info("Friend found with {} : {}", mailFriend, userFriend);

            /* Get my friend list and add my new friend in this one */
            List<User> myFriends = me.getFriends();
            myFriends.add(userFriend);

            /* Set the friends list in a User object and save this one */
            me.setFriends(myFriends);
            try {
                userRepository.save(me);
                logger.info("New friend added");
                return new RedirectView("/addFriend?status=success");
            } catch (Exception e) {
                logger.error("Impossible to save a user with this friend : {} Error : {}", userFriend, e.getMessage());
            }
        }

        return new RedirectView("/addFriend?status=error");
    }

    /**
     *
     * @param request
     * @return a jsp with a status message
     */
    @Override
    public RedirectView deleteFriend(HttpServletRequest request) {

        logger.info("--- Method deleteFriend ---");

        String mailToDelete = request.getParameter("friend");
        HttpSession session = request.getSession();
        User me = userRepository.findByMail((String)session.getAttribute("mail"));
        List<User> myFriendList = userRepository.getMyFriends(me.getId());

        List<User> myNewFriendList = new ArrayList<>();
        /* Take an old friends list and add the friend to it. if the email to be deleted matches, do not add it to the new list */
        for(User user : myFriendList){
            if(!user.getMail().equals(mailToDelete)){
                myNewFriendList.add(user);
            }
        }

        me.setFriends(myNewFriendList);
        try{
            userRepository.save(me);
            logger.info("Friend delete : {}",mailToDelete);
            return new RedirectView("/profile?status=successRemove");
        } catch (Exception e) {
            logger.info("Impossible to delete a friend : {}",e.getMessage());
            return new RedirectView("/profile?status=errorRemove");
        }

    }

    /**
     *
     * @param myMail
     * @param mailFriend
     * @return boolean
     */
    @Override
    public boolean isItMyFriend(String myMail, String mailFriend){

        logger.info("--- Method isItMyFriend ---");

        logger.info("Mail friend {}", mailFriend);

        List<User> myfriends = userRepository.getMyFriends(userRepository.findByMail(myMail).getId());
        logger.info("My friends found : {}", myfriends);

        for(User user : myfriends){
            if(user.getMail().equals(mailFriend)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param mail
     * @return My friend list DTO
     */
    @Override
    public List<FriendDTO> getListOfMyFriends(String mail){

        logger.info("--- Method getListOfMyFriends ---");

        long idByMail = userRepository.findByMail(mail).getId();

        List<User> listFriends = userRepository.getMyFriends(idByMail);

        List<FriendDTO> listFriendsDTO = new ArrayList<>();
        for(User friend : listFriends){
            FriendDTO friendDTO = modelMapper.map(friend, FriendDTO.class);
            listFriendsDTO.add(friendDTO);
        }
        logger.info("List of my FriendDTO : {}", listFriendsDTO);
        return listFriendsDTO;
    }

    /**
     *
     * @param myMail - String
     * @return List of all user DTO without my mail
     */
    @Override
    public List<FriendDTO> getAllUserWithoutMe(String myMail){

        logger.info("--- Method getAllUserWithoutMe ---");

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

    /**
     *
     * @param user - User object
     * @return My profil DTO
     */
    @Override
    public ProfilDTO getProfileDTO(User user){

        logger.info("--- Method getProfileDTO ---");

        User me = userRepository.findByMail(user.getMail());

        try{
            ProfilDTO profileDTO = new ProfilDTO();
            profileDTO = modelMapper.map(me, ProfilDTO.class);

            logger.info("ProfileDTO : {}", profileDTO);
            return profileDTO;
        } catch (Exception e){
            logger.info("User not found {}", user.getMail() + " - " + e.getMessage());
            return null;
        }

    }
}
