package fr.cm.paymybuddy;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.DTO.ProfilDTO;
import fr.cm.paymybuddy.DTO.TransactionDTO;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.RelationServiceInt;
import fr.cm.paymybuddy.Service.Interface.TransactionServiceInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;
import nl.jqno.equalsverifier.EqualsVerifier;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@Profile("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(value = "/UsersDataTest.sql",executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/TransactionsDataTest.sql",executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/truncate.sql",executionPhase = AFTER_TEST_METHOD)
public class DtoTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationServiceInt relationService;

    @Autowired
    TransactionServiceInt transactionService;

    private User user;

    @Test
    @Transactional
    public void userFriendProfileDTOTest() {

        user = userRepository.findByMail("profile@paymybuddy.com");
        ProfilDTO profileControl =  relationService.getProfileDTO(user);

        assertEquals("", "profile@paymybuddy.com", profileControl.getMail());
        assertEquals("", "Adrien", profileControl.getFirstname());
        assertEquals("", "Vier", profileControl.getLastname());
        assertEquals("", 20.0, profileControl.getAccountBalance());
    }

    @Test
    public void simpleEqualsContract() {
        User me = userRepository.findByMail("me@paymybuddy.com");
        User friend = userRepository.findByMail("profile@paymybuddy.com");

        assertEquals("",me == friend, me.equals(friend));
    }

    @Test
    public void transactionDTOTest() {

        List<TransactionDTO> transacDtoList = transactionService.historyTransaction(1,0,3);
      /*  TransactionDTO transactionDTO = transacDtoList.get(2);

        assertEquals("", "Adrien", transactionDTO.getFriendFirstname());
        assertEquals("", "Present", transactionDTO.getDescription());
        assertEquals("", 30.0, transactionDTO.getAmount());

*/
    }
    // getListOfMyFriends
    // historyTransaction

}
