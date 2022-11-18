package fr.cm.paymybuddy.Service.Interface;

import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.DTO.TransfertDTO;
import fr.cm.paymybuddy.DTO.UserDTO;

import javax.servlet.http.HttpServletRequest;

public interface TransactionServiceInt {

    public boolean refundAccount(HttpServletRequest request);

    public boolean sendMoney(HttpServletRequest request);
    public boolean reloadFund(double amount);
    public boolean transfertFund(TransfertDTO transfertDTO);
    public boolean haveIEnoughMoney (UserDTO userDTO, double amount);
    public TransactionDTO historyTransaction();

}
