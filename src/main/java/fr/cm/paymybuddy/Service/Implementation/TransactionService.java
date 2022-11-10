package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.DTO.TransfertDTO;
import fr.cm.paymybuddy.DTO.UserDTO;
import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements TransactionServiceInt {

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
