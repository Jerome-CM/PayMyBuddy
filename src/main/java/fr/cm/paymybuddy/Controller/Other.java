package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/accessDenied")
    public RedirectView accessDenied(HttpServletRequest request){
        return otherService.accesDenied(request);
    }
}
