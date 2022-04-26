package com.eventus.backend.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.StripeService;
import com.eventus.backend.services.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    private String token1 = "";

    private String token2 = "";

    private Participation participation1;

    private User user1;

    private User user2;

    private Event event;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        user1 = new User();
        user1.setId(997L);
        user1.setFirstName("Pepe");
        user1.setLastName("Rodriguez");
        user1.setAdmin(false);
        user1.setEmail("peperod@email.example");
        user1.setPassword("$up3r$€cr3tP4s$w0rd");

        user2 = new User();
        user2.setId(998L);
        user2.setFirstName("Pepe");
        user2.setLastName("padilla");
        user2.setAdmin(false);
        user2.setEmail("peperod2@email.example");
        user2.setPassword("$up3r$€cr3tP4s$w0rd");

        event= new Event(9L, "title", 0., "description");

        participation1 = new Participation();
        participation1.setId(1L);
        participation1.setUser(user1);
        participation1.setEvent(event);

        this.token1 = userService.login("admin@gmail.com", "admin").get();
        this.token2 = userService.login("juan@gmail.com", "test").get();
    }

    @Test
    public void testFindUserParticipationsAdmin() throws Exception {
        // ParticipationService partServ = Mockito.mock(ParticipationService.class);
        List<Participation> participations = List.of(participation1);
        when(participationService.findAllParticipation(PageRequest.of(0, 20000))).thenReturn(participations);
        MvcResult result = this.mockMvc.perform(get("/user/participations").header("Authorization", "Bearer " + token1)).andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(
                mapper.writeValueAsString(participations));
    }

    @Test
    public void testFindUserParticipationsUser() throws Exception {
        // ParticipationService partServ = Mockito.mock(ParticipationService.class);
        List<Participation> participations = List.of(participation1);
        when(participationService.findParticipationByUserId(1L,PageRequest.of(0, 20000))).thenReturn(participations);
        MvcResult result = this.mockMvc.perform(get("/user/participations").header("Authorization", "Bearer " + token2)).andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(
                mapper.writeValueAsString(participations));
    }

    @Test
    public void testFindParticipationById() throws Exception {
        when(this.participationService.findParticipationById(1L)).thenReturn(participation1);
        MvcResult result=this.mockMvc.perform(get("/participations/1").header("Authorization", "Bearer " + token1)).andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(
                mapper.writeValueAsString(participation1));
    }

    @Test
    public void testFindParticipationByIdNotFound() throws Exception {
        when(this.participationService.findParticipationById(1L)).thenReturn(null);
        this.mockMvc.perform(get("/participations/1").header("Authorization", "Bearer " + token1)).andExpect(status().isNotFound());
    }

    @Test
    public void testFindParticipationByIdForbidden() throws Exception {
        when(this.participationService.findParticipationById(1L)).thenReturn(participation1);
        this.mockMvc.perform(get("/participations/1").header("Authorization", "Bearer " + token2)).andExpect(status().is(403));
    }

    @Test
    public void testCreateParticipation() throws Exception {
        when(this.eventService.findById(event.getId())).thenReturn(event);
        when(this.participationService.findByUserIdEqualsAndEventIdEquals(1L, event.getId())).thenReturn(null);
        PaymentMethodCollection paymentMethodCollection=new PaymentMethodCollection();
        paymentMethodCollection.setData(List.of(new PaymentMethod()));
        when(this.stripeService.getPaymentMethods(any(User.class))).thenReturn(paymentMethodCollection);
        when(this.participationService.createTicketPDF(any())).thenReturn(new byte[0]);
        this.mockMvc.perform(post("/participations").header("Authorization", "Bearer " + token2).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(Map.of("eventId",event.getId())))).andExpect(status().isOk());
    }

    @Test
    public void testCreateParticipationNoPaymentMethod() throws Exception {
        when(this.eventService.findById(event.getId())).thenReturn(event);
        when(this.participationService.findByUserIdEqualsAndEventIdEquals(1L, event.getId())).thenReturn(null);
        PaymentMethodCollection paymentMethodCollection=new PaymentMethodCollection();
        paymentMethodCollection.setData(new ArrayList<>());
        when(this.stripeService.getPaymentMethods(any(User.class))).thenReturn(paymentMethodCollection);
        when(this.participationService.createTicketPDF(any())).thenReturn(new byte[0]);
        this.mockMvc.perform(post("/participations").header("Authorization", "Bearer " + token2).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(Map.of("eventId",event.getId())))).andExpect(status().is(402));
    }

    @Test
    public void testCreateParticipationExistingParticipation() throws Exception {
        when(this.eventService.findById(event.getId())).thenReturn(event);
        when(this.participationService.findByUserIdEqualsAndEventIdEquals(1L, event.getId())).thenReturn(participation1);
        PaymentMethodCollection paymentMethodCollection=new PaymentMethodCollection();
        paymentMethodCollection.setData(List.of(new PaymentMethod()));
        when(this.stripeService.getPaymentMethods(any(User.class))).thenReturn(paymentMethodCollection);
        when(this.participationService.createTicketPDF(any())).thenReturn(new byte[0]);
        this.mockMvc.perform(post("/participations").header("Authorization", "Bearer " + token2).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(Map.of("eventId",event.getId())))).andExpect(status().isBadRequest());
    }
    @Test
    public void testDeleteParticipation() throws Exception {
        doNothing().when(this.participationService).deleteParticipation(eq(participation1.getId()),any());
        this.mockMvc.perform(delete("/participations/1").header("Authorization", "Bearer " + token2)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteParticipationValidationError() throws Exception {
        doThrow(new IllegalArgumentException("test")).when(this.participationService).deleteParticipation(eq(participation1.getId()),any());
        this.mockMvc.perform(delete("/participations/1").header("Authorization", "Bearer " + token2)).andExpect(status().isBadRequest());
    }

    @Test
    public void generateTicket() throws Exception {
        when(this.participationService.findParticipationById(1L)).thenReturn(participation1);
        when(this.participationService.createTicketPDF(any())).thenReturn(new byte[0]);
        this.mockMvc.perform(get("/participation/1/ticket").header("Authorization", "Bearer " + token1)).andExpect(status().isOk());
    }

    @Test
    public void generateTicketForbidden() throws Exception {
        when(this.participationService.findParticipationById(1L)).thenReturn(participation1);
        when(this.participationService.createTicketPDF(any())).thenReturn(new byte[0]);
        this.mockMvc.perform(get("/participation/1/ticket").header("Authorization", "Bearer " + token2)).andExpect(status().isBadRequest());
    }

    @Test
    public void getUsersByEvent() throws Exception {
        List<User> users = new ArrayList<>(List.of(user1, user2));
        when(this.participationService.findUsersByEventId(1L, PageRequest.of(0, 20000))).thenReturn(users);
        MvcResult result = mockMvc.perform(get("/events/1/participants").header("Authorization", "Bearer " + token1)).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(
                mapper.writeValueAsString(users));
    }

    @Test
    public void getParticipationsByEvent() throws Exception {
        List<Participation> participations = new ArrayList<>(List.of(participation1));
        when(this.participationService.findParticipationByEventId(1L, PageRequest.of(0, 20000))).thenReturn(participations);
        MvcResult result = mockMvc.perform(get("/events/1/participations").header("Authorization", "Bearer " + token1)).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(
                mapper.writeValueAsString(participations));
    }
}
