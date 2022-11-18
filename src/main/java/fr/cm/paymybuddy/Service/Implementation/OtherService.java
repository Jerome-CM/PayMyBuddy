package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class OtherService implements OtherServiceInt {

    @Override
    public boolean sendContactMessage(HttpServletRequest request) {
        return false;
    }
}
