package fr.cm.paymybuddy.Service.Interface;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OtherServiceInt {

    public List<String> accessPath(String url);

    public boolean sendContactMessage(HttpServletRequest request);
}
