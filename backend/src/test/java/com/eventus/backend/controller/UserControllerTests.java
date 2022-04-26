package com.eventus.backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.eventus.backend.models.User;
import com.eventus.backend.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired                           
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;

    User user;

    @Before                          
    public void setUp() {                               
       this.user = new User();

       this.user.setId(1l);
       this.user.setFirstName("Pepe");
       this.user.setLastName("Rodriguez");
       this.user.setAdmin(false);
       this.user.setEmail("peperod@email.example");
       this.user.setPassword("$up3r$€cr3tP4s$w0rd");                                              
    }

    @Test
    public void registerUserTestPositive() throws Exception {
        when(userService.findByEmail(this.user.getEmail())).thenReturn(Optional.empty());
        when(userService.login(this.user.getEmail(), this.user.getPassword())).thenReturn(Optional.of("token445544"));

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(this.user);
        
        this.mockMvc.perform(post("/session/signup")
                            .content(requestJson))
                            .andExpect(status().isOk());
    }
}

