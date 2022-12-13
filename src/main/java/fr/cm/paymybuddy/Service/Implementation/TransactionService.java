package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.TransactionDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService implements TransactionServiceInt {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, ModelMapper modelMapper){
        this.transactionRepository=transactionRepository;
        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    @Transactional
    public RedirectView refundAccount(HttpServletRequest request) {

        logger.info("--- Method refundAccount ---");

        double amount = Utility.stringCommaToDoublePoint(request.getParameter("amount"));
        String description = request.getParameter("description");
        String mail = request.getParameter("mail_hidden");

        logger.info("Refund request : {}€ {} {}", amount, description, mail);
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
            return new RedirectView("/profile?status=errorRefund");
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

        logger.info("Object Transaction ready to be save : {}", t);

        listTransac.add(t);
        user.setTransactions(listTransac);
        logger.info("All infos users ready to be save : {}", user);


        try {
            userRepository.save(user);
            transactionRepository.save(t);
            logger.info("Transaction save in BDD, user balance updated ");
            return new RedirectView("/profile?status=successRefund&amount="+ amount);
        } catch (Exception e) {
            logger.info("Error : {}", e.getMessage());
            return new RedirectView("/profile?status=errorRefund");
        }

    }

    @Override
    @Transactional
    public RedirectView sendMoney(HttpServletRequest request) {

        logger.info("--- Method sendMoney ---");

        User me = userRepository.findByMail(request.getParameter("mail_hidden"));
        String mailFriend = request.getParameter("mailFriend");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String description = request.getParameter("description");

        logger.info("Refund request : {}€ {} {}", amount, description, mailFriend);

        if(mailFriend.equals("choose")) {
            logger.info("errorChooseEmpty");
            return new RedirectView("/transfert?status=errorChooseEmpty");
        } else if (amount < 1){
            logger.info("errorLittleAmount");
            return new RedirectView("/transfert?status=errorLittleAmount");
        } else if(haveIEnoughMoney(me.getMail(),amount)){
            List<Transaction> listTransac = me.getTransactions();

            User friend = userRepository.findByMail(mailFriend);
            Transaction t = new Transaction();
            t.setAmount(amount);
            t.setDescription(description);
            t.setUser(me);
            t.setUserFriend(friend);
            t.setTypeTransaction(TypeTransaction.Transfer);
            t.setStatus(TypeStatus.Waiting);
            t.setSoldBeforeTransaction(me.getAccountBalance());
            t.setSoldAfterTransaction(me.getAccountBalance() - amount);
            logger.info("Object Transaction ready to be save : {}", t);

            listTransac.add(t);
            me.setTransactions(listTransac);

            me.setAccountBalance(me.getAccountBalance() - amount);
            friend.setAccountBalance(friend.getAccountBalance() + amount);

            try {
                userRepository.save(me);
                userRepository.save(friend);
                transactionRepository.save(t);
                logger.info("Transaction save in BDD, users balances updated ");
                return new RedirectView("/transfert?status=successTransfer&amount="+ amount+"&friend="+friend.getFirstname());
            } catch (Exception e) {
                logger.info("Error : {}", e.getMessage());
                return new RedirectView("/transfert?status=errorTransfert");
            }

        } else {
            return new RedirectView("/transfert?status=errorNotMoney");
        }
    }

    @Override
    public boolean haveIEnoughMoney(String mail, double amount) {

        logger.info("--- Method haveIEnoughMoney ---");

        double myAmount = userRepository.findByMail(mail).getAccountBalance();

        if(myAmount >= amount){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<TransactionDTO> historyTransaction(long id, int limitStart, int limitEnd) {

        logger.info("--- Method historyTransaction ---");

        List<Transaction> listTransac = transactionRepository.findByIdUser(id, limitStart, limitEnd);

        // logger.info("Transactions list finded : {}", listTransac);
        List<TransactionDTO> listDTO = new ArrayList<>();
        for ( Transaction transaction: listTransac) {
            TransactionDTO tDTO = modelMapper.map(transaction, TransactionDTO.class);
            listDTO.add(tDTO);
        }
        logger.info("Transactions history list returned : {}", listDTO);
        return listDTO;
    }

    @Override
    public RedirectView previousPageTransaction(HttpServletRequest request) {

        Integer page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        page = page < 1 ? 1 : page;
        return new RedirectView("/transfert?page="+page);
    }

    @Override
    public RedirectView nextPageTransaction(HttpServletRequest request) {

        Integer page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        return new RedirectView("/transfert?page="+page);
    }
}
