package com.eventus.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.ParticipationRepository;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.StripeService;
import com.itextpdf.text.DocumentException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
class ParticipationServiceTests {

    @Mock
    private StripeService stripeService;

    @Autowired
    private ParticipationService participationService;

    @Mock
    private ParticipationRepository partRepository;

    private User user1;

    private User user2;

    private Event event;

    @BeforeEach
    public void setUp() {
        participationService = new ParticipationService(partRepository, stripeService);
        user1 = new User();
        user1.setId(997L);
        user1.setFirstName("Pepe");
        user1.setLastName("Rodriguez");
        user1.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
        user1.setAdmin(false);
        user1.setEmail("peperod@email.example");
        user1.setPassword("$up3r$€cr3tP4s$w0rd");

        user2 = new User();
        user2.setId(998L);
        user2.setFirstName("Pepe");
        user2.setLastName("Garcñia");
        user2.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 15));
        user2.setAdmin(false);
        user2.setEmail("peperod2@email.example");
        user2.setPassword("$up3r$€cr3tP4s$w0rd");
        event= new Event(999L, "title", 0., "description");
        event.setStartDate(LocalDateTime.now());
        event.setEndDate(LocalDateTime.now());

    }

    @Test
    @Transactional
    void shouldGetUsersByEvent() throws DataAccessException, StripeException {

        List<User> usersReturned = new ArrayList<>();
        usersReturned.add(user1);
        usersReturned.add(user2);

        when(stripeService.createParticipationPayment(any(Participation.class))).thenReturn(new PaymentIntent());
        when(participationService.findUsersByEventId(event.getId(),PageRequest.of(0,20))).thenReturn(usersReturned);
        
        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);
        List<User> users=participationService.findUsersByEventId(event.getId(),PageRequest.of(0,20));
        assertEquals(2,users.size());

    }
    @Test
    @Transactional
    void shouldGetParticipationByUserId() throws DataAccessException, StripeException{;

        List<Participation> participationReturned = new ArrayList<>(user1.getParticipations());
        participationReturned.add(new Participation());
        

        when(stripeService.createParticipationPayment(any(Participation.class))).thenReturn(new PaymentIntent());
        when(participationService.findParticipationByUserId(user1.getId(),PageRequest.of(0,20))).thenReturn(participationReturned);
        
        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);

        List<Participation> users=participationService.findParticipationByUserId(user1.getId(),PageRequest.of(0,20));
        assertEquals(1,users.size());
    }

    @Test
    @Transactional
    void shouldGetParticipationByEventId() throws DataAccessException, StripeException{
        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);

        List<Participation> users=participationService.findParticipationByEventId(event.getId(),PageRequest.of(0,20));
        assertEquals(0,users.size());
    }

    @Test
    void shouldGetParticipationByUserIdAndEventId() throws DataAccessException, StripeException{
        Participation participation1=new Participation();
        participation1.setId(1L);
        participation1.setEvent(event);
        participation1.setUser(user1);
        Participation participation2=new Participation();
        participation2.setId(2L);
        participation2.setEvent(event);
        participation2.setUser(user2);
        List<Participation> participation = List.of(participation1,participation2);
        when(partRepository.findAll(PageRequest.of(0,20))).thenReturn(participation);
        List<Participation> participationsReturned=participationService.findAllParticipation(PageRequest.of(0,20));
        assertEquals(2,participationsReturned.size());
        assertEquals(participation,participationsReturned);

    }

    @Test
    void shouldGetParticipationById(){
        Participation participation1=new Participation();
        participation1.setId(1L);
        participation1.setEvent(event);
        participation1.setUser(user1);
        when(partRepository.findById(1L)).thenReturn(Optional.of(participation1));
        Participation participationReturned = participationService.findParticipationById(1L);
        assertEquals(participation1,participationReturned);
    }
    @Test
    void shouldCreateTicket() throws StripeException, DocumentException, IOException {
        Participation participation1=new Participation();
        participation1.setId(1L);
        participation1.setBuyDate(LocalDate.now());
        participation1.setTicket("122132");
        participation1.setPrice(2.0);
        participation1.setEvent(event);
        participation1.setUser(user1);
        byte[] ticket=participationService.createTicketPDF(participation1);
        assertNotNull(ticket);


    }
    @Test
    void shouldCreateTicketAndParticipation() throws StripeException, DocumentException, IOException {
        Participation participation1=new Participation();
        participation1.setId(1L);
        participation1.setBuyDate(LocalDate.now());
        participation1.setTicket("122132");
        participation1.setPrice(2.0);
        participation1.setEvent(event);
        participation1.setUser(user1);

        when(stripeService.createParticipationPayment(any(Participation.class))).thenReturn(new PaymentIntent());
        when(partRepository.findByUserIdEqualsAndEventIdEquals(user1.getId(),event.getId())).thenReturn(Optional.of(participation1));
        Participation participation=participationService.createParticipationAndTicket(event,user1);
        assertEquals(participation,participation1);
    }

    @Test
    void shouldDeleteParticipation(){
        Participation participation1=new Participation();
        participation1.setId(1L);
        participation1.setEvent(event);
        participation1.setUser(user1);
        when(partRepository.findById(1L)).thenReturn(Optional.of(participation1));
        user1.setAdmin(false);
        participationService.deleteParticipation(1L,user1);
        user2.setAdmin(true);
        participationService.deleteParticipation(1L,user2);
        verify(partRepository,times(2)).deleteById(1L);
    }

}
