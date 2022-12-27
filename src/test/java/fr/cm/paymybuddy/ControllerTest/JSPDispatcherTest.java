package fr.cm.paymybuddy.ControllerTest;

import fr.cm.paymybuddy.Controller.JSPDispatcher;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@Profile("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JSPDispatcherTest {

    @Autowired
    JSPDispatcher jspDispatcher;

    HttpServletRequest mockRequest;
    ModelMap map;

    @Autowired
    UserRepository userRepository;

    @Mock
    OtherServiceInt otherService;

    @Mock
    User user;

    @BeforeEach
    public void setUpPerTest(){
        mockRequest = mock(HttpServletRequest.class);
        map = new ModelMap();
    }

    @Test
    public void getLoginPageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        String jspName = jspDispatcher.getLogin(mockRequest, map);

        assertEquals("", "login", jspName);
    }

    @Test
    public void getRegisterPageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        String jspName = jspDispatcher.getRegister(mockRequest);

        assertEquals("", "register", jspName);
    }

    @Test
    public void getHomePageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn("/");


        String jspName = jspDispatcher.getHome(mockRequest, map);

        assertEquals("", "home", jspName);
    }

    @Test
    public void getTransferPageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockRequest.getRequestURI()).thenReturn("/transfer");
        mockSession.setAttribute("mail", "me@paymybuddy.com");
        when(mockRequest.getSession()).thenReturn(mockSession);
        //when(mockSession.getAttribute("mail")).thenReturn("me@paymybuddy.com");
        HttpSession session = mockRequest.getSession();
        System.out.println(session.getAttribute("mail"));

        String jspName = jspDispatcher.getTransfert(mockRequest, map);

        assertEquals("", "transfert", jspName);
    }

    @Test
    public void getProfilePageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

       // String jspName = jspDispatcher.getProfile(mockRequest, map);

       // assertEquals("", "profile", jspName);
    }

    @Test
    public void getAddFriendPageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

       // String jspName = jspDispatcher.getAddFriend(mockRequest, map);

       // assertEquals("", "addFriend", jspName);
    }

    @Test
    public void getContactPageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        // String jspName = jspDispatcher.getProfile(mockRequest, map);

       // assertEquals("", "contact", jspName);
    }

    @Test
    public void getManagePageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

       // String jspName = jspDispatcher.getProfile(mockRequest, map);

       // assertEquals("", "manage", jspName);
    }

}
