package com.eventus.backend.service;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.EventRepository;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipationServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected ParticipationService participationService;

    @Autowired
    protected EventRepository eventRepository;

    public static User user2 = new User();
    public static Event event = new Event();

    @Test
    @Transactional
    public void shouldGetUsersByEvent() {
        User user1 = new User();
        user1.setFirstName("Pepe");
        user1.setLastName("Gonzalez");
        userService.saveUser(user1);
        User user2 = new User();
        user2.setFirstName("Alfredo");
        user2.setLastName("Duro");
        userService.saveUser(user2);
        Event event=new Event();
        event.setTitle("EventoTest");
        event.setDescription("EventoTest");
        eventRepository.save(event);

        Participation participation=new Participation();
        participation.setUser(user1);
        participation.setEvent(event);
        participationService.saveParticipation(participation);
        Participation participation2=new Participation();
        participation2.setUser(user2);
        participation2.setEvent(event);
        participationService.saveParticipation(participation2);
        List<User> users=participationService.findUsersByEventId(event.getId(),PageRequest.of(0,3));
        assertEquals(users.size(),2);

    }
    @Test
    @Transactional
    public void shouldGetParticipationByUserId(){
        User user1 = new User();
        user1.setFirstName("Pepe");
        user1.setLastName("Gonzalez");
        userService.saveUser(user1);
        User user2 = new User();
        user2.setFirstName("Alfredo");
        user2.setLastName("Duro");
        userService.saveUser(user2);
        Event event=new Event();
        event.setTitle("EventoTest");
        event.setDescription("EventoTest");
        eventRepository.save(event);

        Participation participation=new Participation();
        participation.setUser(user1);
        participation.setEvent(event);
        participationService.saveParticipation(participation);
        Participation participation2=new Participation();
        participation2.setUser(user2);
        participation2.setEvent(event);
        participationService.saveParticipation(participation2);

        List<Participation> users=participationService.findParticipationByUserId(user1.getId(),PageRequest.of(0,3));
        assertEquals(users.size(),1);
    }

    @Test
    @Transactional
    public void shouldGetParticipationByEventId(){
        User user1 = new User();
        user1.setFirstName("Pepe");
        user1.setLastName("Gonzalez");
        userService.saveUser(user1);
        User user2 = new User();
        user2.setFirstName("Alfredo");
        user2.setLastName("Duro");
        userService.saveUser(user2);
        Event event=new Event();
        event.setTitle("EventoTest");
        event.setDescription("EventoTest");
        eventRepository.save(event);

        Participation participation=new Participation();
        participation.setUser(user1);
        participation.setEvent(event);
        participationService.saveParticipation(participation);
        Participation participation2=new Participation();
        participation2.setUser(user2);
        participation2.setEvent(event);
        participationService.saveParticipation(participation2);

        List<Participation> users=participationService.findParticipationByEventId(event.getId(),PageRequest.of(0,3));
        assertEquals(users.size(),2);
    }

}
