package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Other {

    @Autowired
    OtherServiceInt otherService;

    @PostMapping("/sendContactMessage")
    public boolean sendContactMessage(HttpServletRequest request){
        return otherService.sendContactMessage(request);
    }

}
