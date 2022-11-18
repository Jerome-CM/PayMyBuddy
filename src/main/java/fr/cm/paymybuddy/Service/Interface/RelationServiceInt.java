package fr.cm.paymybuddy.Service.Interface;

import fr.cm.paymybuddy.DTO.FriendDTO;

import javax.servlet.http.HttpServletRequest;

public interface RelationServiceInt {

    public boolean register(HttpServletRequest request);

    public boolean modifyUserInfos(HttpServletRequest request);

    public boolean modifyUserPassword(HttpServletRequest request);

    public boolean addFriend(HttpServletRequest request);

    public boolean deleteFriend(FriendDTO friendDTO);

    public boolean isItMyFriend(String mail);

}
