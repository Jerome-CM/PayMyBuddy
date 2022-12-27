package fr.cm.paymybuddy.ControllerTest;

import fr.cm.paymybuddy.Controller.JSPDispatcher;
import fr.cm.paymybuddy.Utility.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Profile("test")
@SpringBootTest
@AutoConfigureMockMvc
public class errorMessageTest {

    HttpServletRequest mockRequest;
    ModelMap map;

    @Autowired
    JSPDispatcher jspDispatcher;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUpPerTest(){
        mockRequest = mock(HttpServletRequest.class);
        map = new ModelMap();
    }

    @Test
    public void notificationsContact() throws Exception {

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("status")).thenReturn("sentMessage");


    }


}
