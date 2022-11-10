package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import org.springframework.stereotype.Service;

@Service
public class RelationService implements RelationServiceInt {

    @Override
    public FriendDTO addFriend(FriendDTO friendDTO) {
        return null;
    }

    @Override
    public FriendDTO deleteFriend(FriendDTO friendDTO) {
        return null;
    }

    @Override
    public boolean isItMyFriend(String mail) {
        return false;
    }
}
