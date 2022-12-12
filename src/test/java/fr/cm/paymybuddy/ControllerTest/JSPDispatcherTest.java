package fr.cm.paymybuddy.ControllerTest;

import fr.cm.paymybuddy.Controller.JSPDispatcher;
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

import java.net.URL;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@Profile("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JSPDispatcherTest {

    @Autowired
    JSPDispatcher jspDispatcher;

    HttpServletRequest mockRequest;
    ModelMap map;

    @Mock
    OtherServiceInt otherService;

    @BeforeEach
    public void setUpPerTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        ModelMap map = new ModelMap();
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
        when(otherService.accessPath(any(String.class))).thenReturn(Collections.singletonList("/"));

        String jspName = jspDispatcher.getHome(mockRequest, map, mockRequest.getUserPrincipal());

        assertEquals("", "/", jspName);
    }

    @Test
    public void getTransferPageTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

       // String jspName = jspDispatcher.getTransfert(mockRequest, map);

       // assertEquals("", "transfert", jspName);
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
