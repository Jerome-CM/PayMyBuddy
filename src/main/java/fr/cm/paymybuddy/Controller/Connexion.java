package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.AccessServiceInt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Connexion {

    private AccessServiceInt accesService;

    public Connexion(AccessServiceInt accesService){
        this.accesService = accesService;
    }

    @PostMapping("/registerUser")
    public RedirectView registerUser(HttpServletRequest request){
        return accesService.register(request);
    }

    @GetMapping("/logout")
    public  RedirectView logoutUser(HttpServletRequest request){
        return accesService.logout(request);
    }


}
