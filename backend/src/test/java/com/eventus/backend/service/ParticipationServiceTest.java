package com.eventus.backend.service;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
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
    protected EventService eventService;

    public static User user2 = new User();
    public static Event event = new Event();

    @Test
    @Transactional
    public void shouldGetUsersByEvent() {
        User user1 = userService.findUserById(1L);
        User user2 = userService.findUserById(2L);
        Event event= eventService.findById(1L);
        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);
        List<User> users=participationService.findUsersByEventId(event.getId(),PageRequest.of(0,20));
        assertEquals(users.size(),11);

    }
    @Test
    @Transactional
    public void shouldGetParticipationByUserId(){
        User user1 = userService.findUserById(1L);
        User user2 = userService.findUserById(2L);
        Event event= eventService.findById(1L);
        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);

        List<Participation> users=participationService.findParticipationByUserId(user1.getId(),PageRequest.of(0,20));
        assertEquals(users.size(),3);
    }

    @Test
    @Transactional
    public void shouldGetParticipationByEventId(){
        User user1 = userService.findUserById(1L);
        User user2 = userService.findUserById(2L);
        Event event= eventService.findById(1L);
        participationService.saveParticipation(event,user1);
        participationService.saveParticipation(event,user2);

        List<Participation> users=participationService.findParticipationByEventId(event.getId(),PageRequest.of(0,20));
        assertEquals(users.size(),9);
    }

}
