package fr.cm.paymybuddy.Service.Interface;

import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.DTO.TransfertDTO;
import fr.cm.paymybuddy.DTO.UserDTO;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TransactionServiceInt {

    public RedirectView refundAccount(HttpServletRequest request);

    public RedirectView sendMoney(HttpServletRequest request);

    public boolean haveIEnoughMoney (UserDTO userDTO, double amount);
    public List<TransactionDTO> historyTransaction(long id);

}
