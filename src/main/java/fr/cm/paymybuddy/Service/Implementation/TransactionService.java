package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.DTO.TransfertDTO;
import fr.cm.paymybuddy.DTO.UserDTO;
import fr.cm.paymybuddy.Model.Transaction;
import fr.cm.paymybuddy.Model.User;
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
        User user = userRepository.findByMail(mail);
        double userBalanceBefore = user.getAccountBalance();

        logger.info("Balance Account before refund : {}", userBalanceBefore);

        double userBalanceAfter = userBalanceBefore + amount;
        logger.info("Balance Account previous after refund : ''{}''", userBalanceAfter);

        try {
            userRepository.updateAccountBalance(userBalanceAfter, mail);
            logger.info("Account Balance update, Balance Account : {}", userBalanceAfter);
        } catch (Exception e) {
            logger.info("Error : {}", e.getMessage());
            return new RedirectView("/profile?status=error");
        }

        // Table Transactions

        List<Transaction> listTransac = user.getTransactions();
        logger.info("Transaction list : {}", listTransac);

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setDescription(description);
        t.setUser(userRepository.findByMail(mail));
        t.setUserFriend(userRepository.findByMail(mail));
        t.setTypeTransaction(TypeTransaction.Refund);
        t.setStatus(TypeStatus.Accepted);
        t.setSoldBeforeTransaction(userBalanceBefore);
        t.setSoldAfterTransaction(userBalanceAfter);

        logger.info("Object Transaction create with setters : {}", t);

        listTransac.add(t);
        user.setTransactions(listTransac);
        logger.info("All infos users ready to be save : {}", user);


        try {
            userRepository.save(user);
            transactionRepository.save(t);
            logger.info("Transaction save in BDD ");
            return new RedirectView("/profile?status=succes&refund=yes&amount="+ amount);
        } catch (Exception e) {
            logger.info("Error : {}", e.getMessage());
            return new RedirectView("/profile?status=error");
        }

    }

    @Override
    public RedirectView sendMoney(HttpServletRequest request) {
        User me = userRepository.findByMail(request.getParameter("mail_hidden"));
        String mailFriend = request.getParameter("mailFriend");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String description = request.getParameter("description");

        if(mailFriend.equals("choose")) {
            return new RedirectView("/transfert?status=errorChooseEmpty");
        } else if (amount < 1){
            return new RedirectView("/transfert?status=errorLittleAmount");
        } else if(haveIEnoughMoney(me.getMail(),amount)){
            List<Transaction> listTransac = me.getTransactions();
            logger.info("Transaction list : {}", listTransac);

            User friend = userRepository.findByMail(mailFriend);
            Transaction t = new Transaction();
            t.setAmount(amount);
            t.setDescription(description);
            t.setUser(me);
            t.setUserFriend(friend);
            t.setTypeTransaction(TypeTransaction.Transfert);
            t.setStatus(TypeStatus.Waiting);
            t.setSoldBeforeTransaction(me.getAccountBalance());
            t.setSoldAfterTransaction(me.getAccountBalance() - amount);
            logger.info("Object Transaction create with setters : {}", t);

            listTransac.add(t);
            me.setTransactions(listTransac);

            me.setAccountBalance(me.getAccountBalance() - amount);
            friend.setAccountBalance(friend.getAccountBalance() + amount);

            try {
                userRepository.save(me);
                userRepository.save(friend);
                transactionRepository.save(t);
                logger.info("Transaction save in BDD ");
                return new RedirectView("/transfert?status=success&transfer=yes&amount="+ amount);
            } catch (Exception e) {
                logger.info("Error : {}", e.getMessage());
                return new RedirectView("/transfert?status=error");
            }

        } else {
            return new RedirectView("/transfert?status=errorNotMoney");
        }
    }

    @Override
    public boolean haveIEnoughMoney(String mail, double amount) {
        double myAmount = userRepository.findByMail(mail).getAccountBalance();

        if(myAmount >= amount){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<TransactionDTO> historyTransaction(long id, int limitStart, int limitEnd) {

        List<Transaction> listTransac = transactionRepository.findByIdUser(id, limitStart, limitEnd);

        logger.info("List transac finded : {}", listTransac);
        List<TransactionDTO> listDTO = new ArrayList<>();
        for ( Transaction transaction: listTransac) {
            TransactionDTO tDTO = modelMapper.map(transaction, TransactionDTO.class);
            listDTO.add(tDTO);
        }
        return listDTO;
    }

    @Override
    public RedirectView previousPageTransaction(HttpServletRequest request) {
        Integer page = Integer.parseInt(request.getParameter("page"));
       /* if(request.getParameter("page") != null){
            Integer page = Integer.parseInt(request.getParameter("page"));
            logger.info("page recup : {}", page);
            if(page > 1){
                page = page - 1;
                logger.info("page return : {}", page);
                return new RedirectView("/transfert?page="+page);
            } else {
                return new RedirectView("/transfert");
            }
        } else {
            return new RedirectView("/transfert");
        }*/

        return new RedirectView("/transfert?page="+page);
    }

    @Override
    public RedirectView nextPageTransaction(HttpServletRequest request) {
        Integer page = Integer.parseInt(request.getParameter("page"));
      /*  Integer page = 0;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
            page = page + 1;
        }*/
        return new RedirectView("/transfert?page="+page);
    }
}
