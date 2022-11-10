package fr.cm.paymybuddy.DTO;

import fr.cm.paymybuddy.Model.TypeTransaction;
import lombok.Data;

@Data
public class TransfertDTO {


    private UserDTO userWhoSendMoney;
    private FriendDTO userWhoReceivesMoney;
    private double amount;
    private TypeTransaction type;
}
