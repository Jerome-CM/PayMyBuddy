package fr.cm.paymybuddy;

import fr.cm.paymybuddy.Model.Transaction;
import fr.cm.paymybuddy.Model.TypeStatus;
import fr.cm.paymybuddy.Model.TypeTransaction;
import fr.cm.paymybuddy.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@Profile("test")
@SpringBootTest
@Sql(value = "/UsersDataTest.sql",executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/truncate.sql",executionPhase = AFTER_TEST_METHOD)
public class EqualsAndHashCode {

    public User setUser(){

        User user = new User();

        user.setMail("me@paymybuddy.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("psw");
        user.setAccountBalance(0);

        return user;
    }

    public Transaction setTransaction(){

        Transaction transaction = new Transaction();

        transaction.setAmount(20);
        transaction.setDescription("Refund");
        transaction.setTypeTransaction(TypeTransaction.Refund);
        transaction.setStatus(TypeStatus.Accepted);
        transaction.setUser(setUser());
        transaction.setUserFriend(setUser());
        transaction.setSoldBeforeTransaction(0);
        transaction.setSoldAfterTransaction(20);
        transaction.setDateCreation(new Date());

        return transaction;
    }

    public Date setModel(){

        return new Date();

    }

    @Test
    public void transactionTest(){

        Transaction transaction1 = setTransaction();
        Transaction transaction2 = setTransaction();

        assertEquals("", true, transaction1.equals(transaction2));
        assertTrue("", transaction1.hashCode() == transaction2.hashCode());

    }

    @Test
    public void userTest(){

        User user1 = setUser();
        User user2 = setUser();

        assertEquals("", true, user1.equals(user2));
        assertTrue("", user1.hashCode() == user2.hashCode());

    }

    @Test
    public void modelTest(){

        Date date1 = setModel();
        Date date2 = setModel();

        assertEquals("", true, date1.equals(date2));
        assertTrue("", date1.hashCode() == date2.hashCode());

    }
}
