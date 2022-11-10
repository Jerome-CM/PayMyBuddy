package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.Service.Interface.AccesServiceInt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccessService implements AccesServiceInt {

    @Override
    public boolean register(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean login(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean logout(HttpServletRequest request) {
        return false;
    }
}
