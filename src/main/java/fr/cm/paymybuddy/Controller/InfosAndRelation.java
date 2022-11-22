package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class InfosAndRelation {

    @Autowired
    RelationServiceInt relationService;

    @PostMapping("/completeInfosUser")
    public RedirectView registerUser(HttpServletRequest request){
        return relationService.register(request);
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

}
