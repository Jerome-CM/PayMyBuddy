package fr.cm.paymybuddy.Service.Interface;

import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OtherServiceInt {

    public List<String> accessPath(String url);

    public RedirectView sendContactMessage(HttpServletRequest request);

    public RedirectView accesDenied(HttpServletRequest request);
}
