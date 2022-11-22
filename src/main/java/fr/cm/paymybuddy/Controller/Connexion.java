package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.AccesServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Connexion {

    @Autowired
    AccesServiceInt accesService;

    @PostMapping("/loginFormControl")
    public RedirectView loginUser(HttpServletRequest request){
       return accesService.login(request);
    }

    @GetMapping("/logout")
    public  RedirectView logoutUser(HttpServletRequest request){
        return accesService.logout(request);
    }


}
