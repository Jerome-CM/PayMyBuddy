package fr.cm.paymybuddy.Service.Interface;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.RedirectView;

public interface AccessServiceInt {

    public RedirectView login(HttpServletRequest request);

    public RedirectView logout(HttpServletRequest request);

    public boolean isUserExist(String mail);

}
