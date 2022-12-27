package fr.cm.paymybuddy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "me@paymybyddy.com")
public class filterChainTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void userHomePage() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }
    @Test
    public void userRegisterPage() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().isOk());
    }

    @Test
    public void userLoginPage() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    public void userContactPage() throws Exception {
        mockMvc.perform(get("/contact")).andExpect(status().isOk());
    }

    @Test
    public void adminPage() throws Exception {
        mockMvc.perform(get("/admin/manage")).andExpect(status().isForbidden());
    }

}

