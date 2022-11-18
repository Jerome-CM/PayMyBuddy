package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Transaction {

    @Autowired
    TransactionServiceInt transactionService;

    @PostMapping("/refundMyAccount")
    public boolean refundAccount(HttpServletRequest request){
        return transactionService.refundAccount(request);
    }

    @PostMapping("/sendMoney")
    public boolean sendMoney(HttpServletRequest request){
        return transactionService.sendMoney(request);
    }
}
