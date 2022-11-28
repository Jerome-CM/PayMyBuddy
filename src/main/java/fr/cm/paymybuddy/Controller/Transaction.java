package fr.cm.paymybuddy.Controller;

import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Transaction {

    private TransactionServiceInt transactionService;

    public Transaction(TransactionServiceInt transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/refundMyAccount")
    public RedirectView refundAccount(HttpServletRequest request){
        return transactionService.refundAccount(request);
    }

    @PostMapping("/sendMoney")
    public RedirectView sendMoney(HttpServletRequest request){
        return transactionService.sendMoney(request);
    }

    @GetMapping("/previousPageTransaction")
    public RedirectView previousPageTransaction(HttpServletRequest request){
        return transactionService.previousPageTransaction(request);
    }

    @GetMapping("/nextPageTransaction")
    public RedirectView nextPageTransaction(HttpServletRequest request){
        return transactionService.nextPageTransaction(request);
    }

}
