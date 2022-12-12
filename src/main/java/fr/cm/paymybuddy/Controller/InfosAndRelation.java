package fr.cm.paymybuddy.Controller;


import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class InfosAndRelation {

    private RelationServiceInt relationService;

    public InfosAndRelation(RelationServiceInt relationService){
        this.relationService = relationService;
    }

    @PostMapping("/modifyingUserInfos")
    public RedirectView modifyingUserInfos(HttpServletRequest request){
        return relationService.modifyUserInfos(request);
    }

    @PostMapping("/modifyingUserPassword")
    public RedirectView modifyingUserPassword(HttpServletRequest request){
        return relationService.modifyUserPassword(request);
    }

    @PostMapping("/addFriend")
    public RedirectView addFriend(HttpServletRequest request){
        return relationService.addFriend(request);
    }

    @GetMapping("/deleteFriend")
    public RedirectView deleteFriend(HttpServletRequest request){
        return relationService.deleteFriend(request);
    }

}
