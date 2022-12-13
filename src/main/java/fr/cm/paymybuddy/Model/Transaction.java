package fr.cm.paymybuddy.Model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="Transactions")
public class Transaction extends Model{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull
    private double amount;

    @NotNull
    @Column(columnDefinition = "varchar(255) default 'New Transaction'")
    private String description = "New Transaction";

    @JoinColumn(name = "user", nullable = false)
    @ManyToOne
    @ToString.Exclude
    private User user;

    @ManyToOne
    @ToString.Exclude
    @NotNull
    private User userFriend;

    @Column(name="type_transaction")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeTransaction typeTransaction;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeStatus status;
    @NotNull
    @Column(name="sold_before_transaction")
    private double soldBeforeTransaction;
    @NotNull
    @Column(name="sold_after_transaction")
    private double soldAfterTransaction;

}
