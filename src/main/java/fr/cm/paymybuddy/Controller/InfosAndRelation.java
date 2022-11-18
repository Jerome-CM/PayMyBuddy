package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class InfosAndRelation {

    @Autowired
    RelationServiceInt relationService;

    @PostMapping("/completeInfosUser")
    public boolean registerUser(HttpServletRequest request){
        return relationService.register(request);
    }

    @PostMapping("/modifyingUserInfos")
    public boolean modifyingUserInfos(HttpServletRequest request){
        return relationService.modifyUserInfos(request);
    }

    @PostMapping("/modifyingUserPassword")
    public boolean modifyingUserPassword(HttpServletRequest request){
        return relationService.modifyUserPassword(request);
    }

    @PostMapping("/addFriend")
    public boolean addFriend(HttpServletRequest request){
        return relationService.addFriend(request);
    }

}
