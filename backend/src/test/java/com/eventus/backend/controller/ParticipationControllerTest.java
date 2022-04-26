package com.eventus.backend.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.StripeService;
import com.eventus.backend.services.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParticipationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ParticipationService participationService;
    @MockBean
    private EventService eventService;
    @MockBean
    private StripeService stripeService;
    @Autowired
    private UserService userService;


    private String token = "";

    @Before
    public void setUp() {
        this.token = userService.login("admin@gmail.com", "admin").get();
    }

    @Test
    public void testFindUserParticipations() throws Exception {
        // ParticipationService partServ = Mockito.mock(ParticipationService.class);
        when(participationService.findAllParticipation(PageRequest.of(0, 20000))).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/user/participations").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }
}
