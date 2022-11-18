package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.DTO.UserDTO;
import fr.cm.paymybuddy.Service.Interface.AccesServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccesController {

    @Autowired
    AccesServiceInt accesService;

    @PostMapping("/loginFormControl")
    public String addPerson(HttpServletRequest request){
       return accesService.login(request);
    }

}
