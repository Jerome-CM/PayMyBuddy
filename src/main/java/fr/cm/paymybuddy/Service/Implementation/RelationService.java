package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RelationService implements RelationServiceInt {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean register(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean modifyUserInfos(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean modifyUserPassword(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean addFriend(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean deleteFriend(FriendDTO friendDTO) {
        return false;
    }

    @Override
    public boolean isItMyFriend(String mail) {
        return false;
    }
}
