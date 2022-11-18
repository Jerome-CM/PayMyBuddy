package fr.cm.paymybuddy.Service.Interface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.DTO.UserDTO;

public interface AccesServiceInt {

    public void register (HttpServletRequest request);

    public String login(HttpServletRequest request);

    public boolean logout(HttpServletRequest request);
}
