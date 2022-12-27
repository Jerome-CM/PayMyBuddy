package fr.cm.paymybuddy.ControllerTest;

import fr.cm.paymybuddy.Controller.JSPDispatcher;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@Profile("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Sql(value = "/UsersDataTest.sql",executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/truncate.sql",executionPhase = AFTER_TEST_METHOD)
public class JSPDispatcherTest {

    @Autowired
    JSPDispatcher jspDispatcher;

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpSession mockSession;
    ModelMap map;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUpPerTest(){

        map = new ModelMap();
    }

    @Test
    public void getLoginPageTest(){

        String jspName = jspDispatcher.getLogin(mockRequest, map);

        assertEquals("", "login", jspName);
    }

    @Test
    public void getRegisterPageTest(){

        String jspName = jspDispatcher.getRegister(mockRequest);

        assertEquals("", "register", jspName);
    }

    @Test
    public void getHomePageTest(){

        when(mockRequest.getRequestURI()).thenReturn("/");

        String jspName = jspDispatcher.getHome(mockRequest, map);

        assertEquals("", "home", jspName);
    }

    @Test
    public void getTransferPageTest(){
        when(mockRequest.getRequestURI()).thenReturn("/transfer");
        mockSession.setAttribute("mail", "me@paymybuddy.com");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("mail")).thenReturn("me@paymybuddy.com");

        String jspName = jspDispatcher.getTransfert(mockRequest, map);

        assertEquals("", "transfert", jspName);
    }

    @Test
    public void getProfilePageTest(){
        when(mockRequest.getRequestURI()).thenReturn("/profile");
        mockSession.setAttribute("mail", "me@paymybuddy.com");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("mail")).thenReturn("me@paymybuddy.com");

        String jspName = jspDispatcher.getProfile(mockRequest, map);

        assertEquals("", "profile", jspName);
    }

    @Test
    public void getAddFriendPageTest(){
        when(mockRequest.getRequestURI()).thenReturn("/addFriend");
        mockSession.setAttribute("mail", "me@paymybuddy.com");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("mail")).thenReturn("me@paymybuddy.com");

        String jspName = jspDispatcher.getAddFriend(mockRequest, map);

        assertEquals("", "addFriend", jspName);
    }

    @Test
    public void getContactPageTest(){
        when(mockRequest.getRequestURI()).thenReturn("/contact");

        String jspName = jspDispatcher.getContact(mockRequest, map);

        assertEquals("", "contact", jspName);
    }

    @Test
    public void getManagePageTest(){
        when(mockRequest.getRequestURI()).thenReturn("/admin/manage");

        String jspName = jspDispatcher.getManage(mockRequest, map);

        assertEquals("", "manage", jspName);
    }

}
