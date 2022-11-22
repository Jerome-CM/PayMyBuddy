package fr.cm.paymybuddy.Model;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="Users")
public class User extends Model{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String firstname = "Not set";
    private String lastname = "Not set";
    @Column(unique = true, length = 120)

    private String mail;
    private String password;

    @Column(name="account_balance")
    private double accountBalance = 0.00;

    @ManyToMany
    List<User> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Transaction> transactions = new ArrayList<>();

}
