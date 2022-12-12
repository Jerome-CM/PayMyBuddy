package fr.cm.paymybuddy.Service.Interface;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.DTO.ProfilDTO;
import fr.cm.paymybuddy.Model.User;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RelationServiceInt {



    public RedirectView modifyUserInfos(HttpServletRequest request);

    public RedirectView modifyUserPassword(HttpServletRequest request);

    public RedirectView addFriend(HttpServletRequest request);

    public RedirectView deleteFriend(HttpServletRequest request);

    public boolean isItMyFriend(String myMail, String mailFriend);

    public List<FriendDTO> getListOfMyFriends(String mail);

    public List<FriendDTO> getAllUserWithoutMe(String myMail);

    public ProfilDTO getProfileDTO(User user);

}
