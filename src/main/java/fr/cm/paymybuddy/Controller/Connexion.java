package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.AccesServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Connexion {

    @Autowired
    AccesServiceInt accesService;

    @PostMapping("/loginFormControl")
    public String loginUser(HttpServletRequest request){
       return accesService.login(request);
    }

    @GetMapping("/logout")
    public boolean logoutUser(HttpServletRequest request){
        return accesService.logout(request);
    }


}
