package fr.cm.paymybuddy.Service.Interface;

import fr.cm.paymybuddy.DTO.FriendDTO;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public interface RelationServiceInt {

    public RedirectView register(HttpServletRequest request);

    public RedirectView modifyUserInfos(HttpServletRequest request);

    public RedirectView modifyUserPassword(HttpServletRequest request);

    public RedirectView addFriend(HttpServletRequest request);

    public RedirectView deleteFriend(FriendDTO friendDTO);

    public boolean isItMyFriend(String mail);

}
