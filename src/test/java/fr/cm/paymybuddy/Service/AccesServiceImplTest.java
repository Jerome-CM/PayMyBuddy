package fr.cm.paymybuddy.Service;

import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.AccessServiceInt;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

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
@Sql(value = "/truncate.sql",executionPhase = AFTER_TEST_METHOD)
public class AccesServiceImplTest {

    @Autowired
    AccessServiceInt accessService;

    @Autowired
    UserRepository userRepository;

    @Mock
    User user;

    @Test
    public void register(){

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("firstname")).thenReturn("John");
        when(mockRequest.getParameter("lastname")).thenReturn("Doe");
        when(mockRequest.getParameter("mail")).thenReturn("test@test.com");
        when(mockRequest.getParameter("password")).thenReturn("passWord");


        RedirectView url = accessService.register(mockRequest);
        System.out.println(url);
        User userFinded = userRepository.findByMail("test@test.com");

        assertEquals("", "John", userFinded.getFirstname());
        assertEquals("", "Doe", userFinded.getLastname());
        assertEquals("", "test@test.com", userFinded.getMail());
        assertEquals("", "USER", userFinded.getRole());
        assertNotEquals("","passWord", userFinded.getPassword());

    }

    @Test
    public void isUserExistTest(){

        boolean response = accessService.isUserExist("me@paymybuddy.com");

        assertEquals("", true, response);

    }

}
