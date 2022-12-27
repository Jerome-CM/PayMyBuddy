package fr.cm.paymybuddy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@SpringBootTest
@AutoConfigureMockMvc
public class filterChainUnknowTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void adminPage() throws Exception {
        mockMvc.perform(get("/admin/manage")).andExpect(status().is3xxRedirection());
    }

}

