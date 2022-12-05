package fr.cm.paymybuddy.Service;

import fr.cm.paymybuddy.DTO.FriendDTO;
import fr.cm.paymybuddy.Model.Transaction;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.TransactionRepository;
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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@Profile("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(value = "/UsersDataTest.sql",executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/truncate.sql",executionPhase = AFTER_TEST_METHOD)
public class RelationServiceTest {

  /*  @Autowired
    TransactionServiceInt transactionService;
    @Autowired
    TransactionRepository transactionRepository;*/

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationServiceInt relationService;

    private User me;
    private User myFriend;

    @BeforeEach
    public void setUpEntities(){
        me = userRepository.findByMail("me@paymybuddy.com");
        myFriend = userRepository.findByMail("profile@paymybuddy.com");

    }
    @Test
    public void registerTest(){

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("mail")).thenReturn("me@paymybuddy.com");
        when(mockRequest.getParameter("firstname")).thenReturn("John");
        when(mockRequest.getParameter("lastname")).thenReturn("Doe");

        String oldLastname = me.getLastname();
        RedirectView url = relationService.register(mockRequest);

        me = userRepository.findByMail("me@paymybuddy.com");

        assertEquals("", null, oldLastname);
        assertEquals("","John", me.getFirstname());
        assertEquals("","Doe", me.getLastname());
        assertNotNull("", me.getDateModification());
        assertEquals("", "/transfert?status=successRegister", url.getUrl());
    }

   /* @Test
    public void modifyUserInfosTest(){

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("mail")).thenReturn("manage@paymybuddy.com");
        when(mockRequest.getParameter("mail_hidden")).thenReturn("contact@paymybuddy.com");
        when(mockRequest.getParameter("firstname")).thenReturn("Johny");
        when(mockRequest.getParameter("lastname")).thenReturn("Jack");
        User userOldInfo = userRepository.findByMail("contact@paymybuddy.com");

        RedirectView url = relationService.modifyUserInfos(mockRequest);
        User userNewInfo = userRepository.findByMail("manage@paymybuddy.com");

        assertEquals("", null, userOldInfo.getFirstname());
        assertEquals("","Johny", userNewInfo.getFirstname());
        assertEquals("", null, userOldInfo.getLastname());
        assertEquals("","Jack", userNewInfo.getLastname());
        assertEquals("", "contact@paymybuddy.com", userOldInfo.getMail());
        assertEquals("","manage@paymybuddy.com", userNewInfo.getMail());
        assertNotEquals(userOldInfo.getDateModification(), userNewInfo.getDateModification());
        assertEquals("", "/profile?status=successModifInfos",url.getUrl());
    }*/

    @Test
    public void modifyUserPasswordTest(){

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("mail_hidden")).thenReturn("me@paymybuddy.com");
        when(mockRequest.getParameter("password")).thenReturn("test");
        when(mockRequest.getParameter("confirmPassword")).thenReturn("test");

        String oldPass = me.getPassword();

        RedirectView url = relationService.modifyUserPassword(mockRequest);

        me = userRepository.findByMail("me@paymybuddy.com");
        String newPass = me.getPassword();

        assertNotEquals(oldPass, newPass);
        assertEquals("", "/profile?status=successChangePassword", url.getUrl());
    }

    // TODO Soucis de Lazy
    /*
    @Test
    public void addFriendTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("mail_hidden")).thenReturn("me@paymybuddy.com");
        when(mockRequest.getParameter("mail")).thenReturn("profile@paymybuddy.com");

        int nbrFriendBefore = me.getFriends().size();

        RedirectView url = relationService.addFriend(mockRequest);

        me = userRepository.findByMail("me@paymybuddy.com");

        List<User> friendList = me.getFriends();

        assertEquals("", 0, nbrFriendBefore);
        assertEquals("", 1, friendList.size());
        assertEquals("","profile@paymybuddy.com", friendList.get(0).getMail());
        assertEquals("", "/addFriend?status=success", url.getUrl());

    }

    @Test
    public void deleteFriendTest(){
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getAttribute("mail")).thenReturn("me@paymybuddy.com");
        when(mockRequest.getParameter("friend")).thenReturn("home@paymybuddy.com");

        RedirectView url = relationService.deleteFriend(mockRequest);
        User me = userRepository.findByMail("me@paymybuddy.com");

        assertEquals("",0, me.getFriends().size());
        assertEquals("", "/profile?status=successRemove", url.getUrl());
    }

    @Test
    public void isItMyFriendTest(){

         this.addFriend();

        String myMail = "me@paymybuddy.com";
        String mailFriend = "profile@paymybuddy.com";

        assertEquals("", true,relationService.isItMyFriend(myMail,mailFriend));

    }*/


    @Test
    public void getAllUserWithoutMeTest() {

        String myMail = "me@paymybuddy.com";
        List<FriendDTO> userDtoList = relationService.getAllUserWithoutMe(myMail);

        List<User> allUsersList = (List<User>) userRepository.findAll();

        assertEquals("",userDtoList.size(), allUsersList.size() - 1);

    }


}
