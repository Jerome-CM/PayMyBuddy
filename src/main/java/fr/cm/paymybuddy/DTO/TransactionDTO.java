package fr.cm.paymybuddy.DTO;

import lombok.Data;

@Data
public class TransactionDTO {

    private String friendFirstname;
    private String description;
    private double amount;

}
