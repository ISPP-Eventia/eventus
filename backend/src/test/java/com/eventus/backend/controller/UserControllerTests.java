package com.eventus.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

@RunWith(SpringRunner.class)
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
       user = new User();

       user.setId(1l);
       user.setFirstName("Pepe");
       user.setLastName("Rodriguez");
       user.setAdmin(false);
       user.setEmail("peperod@email.example");
       user.setPassword("$up3r$€cr3tP4s$w0rd");                                              
    }

    @Test
    public void registerUserTestNegative() throws Exception {
        
        when(userService.findByEmail(this.user.getEmail())).thenReturn(Optional.empty());
        when(userService.login(this.user.getEmail(), this.user.getPassword())).thenReturn(Optional.of("value"));
        doNothing().when(userService).saveUser(user);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstName", "Pepe");
        requestParams.add("lastName", "Rodriguez");
        requestParams.add("email", "peperod@gmail.com");
        requestParams.add("birthDate", "2000-03-03");
        requestParams.add("password", "$up3r$€cr3tP4s$w0rd");

        this.mockMvc.perform(post("/session/signup")
                            .params(requestParams))
                            .andExpect(status().isBadRequest());
    }

    @Test
    public void registerUserTestNegative2() throws Exception {
        
        when(userService.findByEmail(this.user.getEmail())).thenReturn(Optional.of(user));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstName", "Pepe");
        requestParams.add("lastName", "Rodriguez");
        requestParams.add("admin", "false");
        requestParams.add("email", "peperod@email.example");
        requestParams.add("birthDate", "2000-03-03");
        requestParams.add("password", "$up3r$€cr3tP4s$w0rd");

        this.mockMvc.perform(post("/session/signup")
                            .params(requestParams))
                            .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginNegative() throws Exception{
        when(userService.findByEmail(this.user.getEmail())).thenReturn(Optional.of(user));
        when(userService.login(this.user.getEmail(), this.user.getPassword())).thenReturn(Optional.of("value"));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("email", "peperod@email.example");
        requestParams.add("password", "$up3r$€cr3tP4s$w0rd");

        this.mockMvc.perform(post("/session/login")
                            .params(requestParams))
                            .andExpect(status().is4xxClientError());
    }
}
