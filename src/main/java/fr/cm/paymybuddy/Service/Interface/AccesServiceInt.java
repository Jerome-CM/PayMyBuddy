package fr.cm.paymybuddy.Service.Interface;

import javax.servlet.http.HttpServletRequest;

public interface AccesServiceInt {

    public boolean register (HttpServletRequest request);

    public boolean login(HttpServletRequest request);

    public boolean logout(HttpServletRequest request);
}
