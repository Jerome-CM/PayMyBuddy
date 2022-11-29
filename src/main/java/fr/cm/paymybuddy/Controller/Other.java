package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Other {

    OtherServiceInt otherService;

    public Other(OtherServiceInt otherService){
        this.otherService=otherService;
    }

    @PostMapping("/sendContactMessage")
    public RedirectView sendContactMessage(HttpServletRequest request){
        return otherService.sendContactMessage(request);
    }

}
