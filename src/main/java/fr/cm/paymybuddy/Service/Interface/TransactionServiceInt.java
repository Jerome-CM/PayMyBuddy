package fr.cm.paymybuddy.Service.Interface;

import fr.cm.paymybuddy.DTO.TransactionDTO;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TransactionServiceInt {

    public RedirectView refundAccount(HttpServletRequest request);

    public RedirectView sendMoney(HttpServletRequest request);

    public boolean haveIEnoughMoney(String mail, double amount);

    public List<TransactionDTO> historyTransaction(long id, int limitStart, int limitEnd);

    public RedirectView previousPageTransaction(HttpServletRequest request);

    public RedirectView nextPageTransaction(HttpServletRequest request);

}
