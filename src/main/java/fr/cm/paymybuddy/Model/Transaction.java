package fr.cm.paymybuddy.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
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
    // @ToString.Exclude Generate an implementation of the toString()
    @JsonIgnore
    private User user;

    @ManyToOne
    //@ToString.Exclude Generate an implementation of the toString()
    @JsonIgnore
    @NotNull
    private User userFriend;

    //@Column(name="type_transaction")
    @Enumerated(EnumType.STRING) // Write String, not enum index
    @NotNull
    private TypeTransaction typeTransaction;
    @NotNull
    @Enumerated(EnumType.STRING)// Write String, not enum index
    private TypeStatus status;
    @NotNull
    // @Column(name="sold_before_transaction")
    private double soldBeforeTransaction;
    @NotNull
    // @Column(name="sold_after_transaction")
    private double soldAfterTransaction;

}
