package fr.cm.paymybuddy.Service.Interface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.DTO.UserDTO;
import org.springframework.web.servlet.view.RedirectView;

public interface AccesServiceInt {

    public RedirectView login(HttpServletRequest request);

    public RedirectView logout(HttpServletRequest request);
}
