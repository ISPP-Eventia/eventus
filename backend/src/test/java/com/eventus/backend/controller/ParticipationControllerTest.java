package com.eventus.backend.controller;

import com.eventus.backend.configuration.SecurityConfiguration;
import com.eventus.backend.configuration.TokenAuthenticationProvider;
import com.eventus.backend.controllers.ParticipationController;
import com.eventus.backend.models.Participation;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.StripeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ParticipationController.class)
public class ParticipationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ParticipationService participationService;
    @MockBean
    private EventService eventService;
    @MockBean
    private StripeService stripeService;

    @Test
    public void testGetParticipationById() throws Exception {
        Participation participation = new Participation();
        given(participationService.findParticipationById(1L)).willReturn(participation);
        MvcResult res = this.mockMvc.perform(MockMvcRequestBuilders.get("/1")).andExpect(status().isOk()).andReturn();
    }
}
