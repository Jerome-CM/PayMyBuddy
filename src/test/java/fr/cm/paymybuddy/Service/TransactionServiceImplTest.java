package fr.cm.paymybuddy.Service;

import fr.cm.paymybuddy.Model.Transaction;
import fr.cm.paymybuddy.Model.TypeStatus;
import fr.cm.paymybuddy.Model.TypeTransaction;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.TransactionRepository;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import fr.cm.paymybuddy.Utility.Utility;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

@Profile("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(value = "/UsersDataTest.sql",executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/TransactionsDataTest.sql",executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/truncate.sql",executionPhase = AFTER_TEST_METHOD)
public class TransactionServiceImplTest {

    @Autowired
    TransactionServiceInt transactionService;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    private User me;
    private User myFriend;

    private Transaction transac;

    @BeforeEach
    public void setUpEntities(){
        me = userRepository.findByMail("me@paymybuddy.com");
        myFriend = userRepository.findByMail("profile@paymybuddy.com");
        transac = new Transaction();
    }

    @Test
    @Transactional
    public void refundAccountTest(){

        List<Transaction> transacList = (List<Transaction>) transactionRepository.findAll();
        int nbrTransac = transacList.size();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("mail_hidden")).thenReturn("me@paymybuddy.com");
        when(mockRequest.getParameter("amount")).thenReturn("50,00");
        when(mockRequest.getParameter("description")).thenReturn("Refund");
        double amount = Utility.stringCommaToDoublePoint(mockRequest.getParameter("amount"));

        RedirectView url = transactionService.refundAccount(mockRequest);
        String urlExpected = "/profile?status=successRefund&amount="+amount;
        transacList = (List<Transaction>) transactionRepository.findAll();

        assertEquals("",nbrTransac + 1,transacList.size());
      //  assertEquals("", me.getAccountBalance() + amount, userRepository.findByMail("me@paymybuddy.com").getAccountBalance());

        Transaction transaction = transactionRepository.findAllByUserId(1).get(1);
        assertEquals("",transaction.getUser(), transaction.getUserFriend());
        assertEquals("",urlExpected, url.getUrl());
    }

    @Test
    public void sendMoneyTest(){

        List<Transaction> transacList = (List<Transaction>) transactionRepository.findAll();
        int nbrTransac = transacList.size();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("mailFriend")).thenReturn("profile@paymybuddy.com");
        when(mockRequest.getParameter("mail_hidden")).thenReturn("me@paymybuddy.com");
        when(mockRequest.getParameter("amount")).thenReturn("50");
        when(mockRequest.getParameter("description")).thenReturn("A big present");

        double amount = Utility.stringCommaToDoublePoint(mockRequest.getParameter("amount"));

        RedirectView url = transactionService.sendMoney(mockRequest);
        String urlExpected = "/transfert?status=successTransfer&amount="+ amount+"&friend="+myFriend.getFirstname();

        transacList = (List<Transaction>) transactionRepository.findAll();

        assertEquals("",nbrTransac + 1,transacList.size());
        assertEquals("", me.getAccountBalance() - amount, userRepository.findByMail("me@paymybuddy.com").getAccountBalance());
        assertEquals("", myFriend.getAccountBalance() + amount, userRepository.findByMail("profile@paymybuddy.com").getAccountBalance());
        Transaction transaction = transactionRepository.findAllByUserId(1).get(1);
        assertNotEquals("",transaction.getUser(), transaction.getUserFriend());
        assertEquals("",urlExpected, url.getUrl());

    }
}
