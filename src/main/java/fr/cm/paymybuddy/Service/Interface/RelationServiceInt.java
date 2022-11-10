package fr.cm.paymybuddy.Service.Interface;

import fr.cm.paymybuddy.DTO.FriendDTO;

public interface RelationServiceInt {

    public FriendDTO addFriend(FriendDTO friendDTO);
    public FriendDTO deleteFriend(FriendDTO friendDTO);
    public boolean isItMyFriend(String mail);

}
