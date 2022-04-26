package com.eventus.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.ParticipationRepository;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ParticipationServiceTests {

    @Mock
    private StripeService stripeService;

    @Autowired
    private ParticipationService participationService;

    @Mock
    private ParticipationRepository partRepository;

    @Before
    public void setUp() {
        participationService = new ParticipationService(partRepository, stripeService);
    }

    @Test
    @Transactional
    public void shouldGetUsersByEvent() throws DataAccessException, StripeException {
        User user1 = new User();
        user1.setId(997L);
        user1.setFirstName("Pepe");
        user1.setLastName("Rodriguez");
        user1.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
        user1.setAdmin(false);
        user1.setEmail("peperod@email.example");
        user1.setPassword("$up3r$€cr3tP4s$w0rd");
        
        User user2 = new User();
        user2.setId(998L);
        user2.setFirstName("Pepe");
        user2.setLastName("Garcñia");
        user2.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 15));
        user2.setAdmin(false);
        user2.setEmail("peperod2@email.example");
        user2.setPassword("$up3r$€cr3tP4s$w0rd");
        Event event= new Event(999L, "title", 0., "description");

        List<User> usersReturned = new ArrayList<>();
        usersReturned.add(user1);
        usersReturned.add(user2);

        when(stripeService.createParticipationPayment(any(Participation.class))).thenReturn(new PaymentIntent());
        when(participationService.findUsersByEventId(event.getId(),PageRequest.of(0,20))).thenReturn(usersReturned);
        
        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);
        List<User> users=participationService.findUsersByEventId(event.getId(),PageRequest.of(0,20));
        assertEquals(users.size(),2);

    }
    @Test
    @Transactional
    public void shouldGetParticipationByUserId() throws DataAccessException, StripeException{
        User user1 = new User();
        user1.setId(997L);
        user1.setFirstName("Pepe");
        user1.setLastName("Rodriguez");
        user1.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
        user1.setAdmin(false);
        user1.setEmail("peperod@email.example");
        user1.setPassword("$up3r$€cr3tP4s$w0rd");
        
        User user2 = new User();
        user2.setId(998L);
        user2.setFirstName("Pepe");
        user2.setLastName("Garcñia");
        user2.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 15));
        user2.setAdmin(false);
        user2.setEmail("peperod2@email.example");
        user2.setPassword("$up3r$€cr3tP4s$w0rd");
        Event event= new Event(999L, "title", 0., "description");

        List<Participation> participationReturned = new ArrayList<>(user1.getParticipations());
        participationReturned.add(new Participation());
        

        when(stripeService.createParticipationPayment(any(Participation.class))).thenReturn(new PaymentIntent());
        when(participationService.findParticipationByUserId(user1.getId(),PageRequest.of(0,20))).thenReturn(participationReturned);
        
        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);

        List<Participation> users=participationService.findParticipationByUserId(user1.getId(),PageRequest.of(0,20));
        assertEquals(users.size(),1);
    }

    @Test
    @Transactional
    public void shouldGetParticipationByEventId() throws DataAccessException, StripeException{
        User user1 = new User();
        user1.setId(997L);
        user1.setFirstName("Pepe");
        user1.setLastName("Rodriguez");
        user1.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
        user1.setAdmin(false);
        user1.setEmail("peperod@email.example");
        user1.setPassword("$up3r$€cr3tP4s$w0rd");
        
        User user2 = new User();
        user2.setId(998L);
        user2.setFirstName("Pepe");
        user2.setLastName("Garcñia");
        user2.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 15));
        user2.setAdmin(false);
        user2.setEmail("peperod2@email.example");
        user2.setPassword("$up3r$€cr3tP4s$w0rd");
        Event event= new Event(999L, "title", 0., "description");

        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);

        List<Participation> users=participationService.findParticipationByEventId(event.getId(),PageRequest.of(0,20));
        assertEquals(users.size(),0);
    }

}
