package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.DTO.TransfertDTO;
import fr.cm.paymybuddy.DTO.UserDTO;
import fr.cm.paymybuddy.Model.Transaction;
import fr.cm.paymybuddy.Model.TypeStatus;
import fr.cm.paymybuddy.Model.TypeTransaction;
import fr.cm.paymybuddy.Repository.TransactionRepository;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import fr.cm.paymybuddy.Utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService implements TransactionServiceInt {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public RedirectView refundAccount(HttpServletRequest request) {

        double amount = Utility.stringCommaToDoublePoint(request.getParameter("amount"));
        String description = request.getParameter("description");
        String mail = request.getParameter("mail_hidden");

        logger.info("Data send from form : {} {} {}", amount, description, mail);
        // table Users
        double userBalanceBefore = userRepository.findByMail(mail).getAccountBalance();

        logger.info("Balance Account before refund : {}", userBalanceBefore);

        double userBalanceAfter = userBalanceBefore + amount;
        logger.info("Balance Account after refund : {}", userBalanceAfter);

        try {
            userRepository.updateAccountBalance(userBalanceAfter, mail);
            logger.info("Account Balance update");
        } catch (Exception e) {
            logger.info("Error : {}", e.getMessage());
            return new RedirectView("/profile?status=error");
        }

        // Table Transactions

        Transaction t = new Transaction();

        t.setAmount(amount);
        t.setDescription(description);
        t.setMyId(userRepository.findByMail(mail).getId());
        t.setIdFriend(userRepository.findByMail(mail).getId());
        t.setTypeTransaction(TypeTransaction.Refund);
        t.setStatus(TypeStatus.Accepted);
        t.setSoldBeforeTransaction(userBalanceBefore);
        t.setSoldAfterTransaction(userBalanceAfter);
        t.setDateCreation(new Date());

        logger.info("Object Transaction create with setters : {}", t);

        try {
            transactionRepository.save(t);
            logger.info("Transcation save in BDD ");
            return new RedirectView("/profile?status=succes&refund=yes&amount="+ amount);
        } catch (Exception e) {
            logger.info("Error : {}", e.getMessage());
            return new RedirectView("/profile?status=error");
        }

    }

    @Override
    public RedirectView sendMoney(HttpServletRequest request) {
        return new RedirectView("/transfert");
    }


    @Override
    public boolean haveIEnoughMoney(UserDTO userDTO, double amount) {
        return false;
    }

    @Override
    public List<TransactionDTO> historyTransaction(long id) {

        List<Transaction> listTransac = transactionRepository.findByIdUser(id);
        List<TransactionDTO> listDTO = new ArrayList<>();
        for ( Transaction transaction: listTransac) {
            new TransactionDTO();
            modelMapper.map(transaction, TransactionDTO.class);
        }
        return listDTO;
    }
}
