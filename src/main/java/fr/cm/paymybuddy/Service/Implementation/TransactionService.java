package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.DTO.TransfertDTO;
import fr.cm.paymybuddy.DTO.UserDTO;
import fr.cm.paymybuddy.Repository.TransactionRepository;
import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TransactionService implements TransactionServiceInt {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public boolean refundAccount(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean sendMoney(HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean reloadFund(double amount) {
        return false;
    }

    @Override
    public boolean transfertFund(TransfertDTO transfertDTO) {
        return false;
    }

    @Override
    public boolean haveIEnoughMoney(UserDTO userDTO, double amount) {
        return false;
    }

    @Override
    public TransactionDTO historyTransaction() {
        return null;
    }
}
