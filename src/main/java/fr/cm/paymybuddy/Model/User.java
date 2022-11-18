package fr.cm.paymybuddy.Model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="Users")
public class User extends Model{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String firstname = "Not set";
    private String lastname = "Not set";
    private String mail;
    private String password;

    @Column(name="account_balance")
    private double accountBalance = 0.00;


}
