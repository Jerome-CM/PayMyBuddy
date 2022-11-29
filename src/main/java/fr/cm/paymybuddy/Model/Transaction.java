package fr.cm.paymybuddy.Model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name="Transactions")
public class Transaction extends Model{


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private double amount;

    private String description;

    @JoinColumn(name = "user", nullable = false)
    @ManyToOne @ToString.Exclude
    private User user;

    @ManyToOne @ToString.Exclude
    private User userFriend;

    @Column(name="type_transaction")
    private TypeTransaction typeTransaction;

    private TypeStatus status;

    @Column(name="sold_before_transaction")
    private double soldBeforeTransaction;

    @Column(name="sold_after_transaction")
    private double soldAfterTransaction;



}
