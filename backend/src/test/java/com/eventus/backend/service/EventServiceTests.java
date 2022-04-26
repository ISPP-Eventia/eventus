package com.eventus.backend.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventServiceTests {

    @Autowired
    protected EventService eventService;

    @Autowired
    protected UserService userService;
    
    @Mock
    Event event;
    User user;

    @Before
    public void setUp(){ 
        this.user = userService.findUserById(3L);

        this.event = new Event();

		event.setId(99L);
		event.setTitle("Event 1");
		event.setDescription("Event 1 for testing");
        event.setStartDate(LocalDateTime.now());
        event.setEndDate(LocalDateTime.now().plusHours(4L));
		event.setPrice(2.00);
		event.setOrganizer(user);

    }


    @Test
    @Transactional
    public void testSaveEventPositive(){

        Event event = eventService.save(this.event);

        assertTrue(event != null);
        assertEquals(this.event, event);
    }

    @Test
    @Transactional
    public void testFindAllEvents(){
        assertNotNull(eventService.findAll(Pageable.ofSize(1)));
        assertEquals(1, eventService.findAll(Pageable.ofSize(1)).size());
    }

    @Test
    @Transactional
    public void testFindAllNotFinishedPositive(){
        assertNotNull(eventService.findAllNotFinished(Pageable.ofSize(1)));
        assertEquals(1, eventService.findAllNotFinished(Pageable.ofSize(1)).size());
    }

    @Test
    @Transactional
    public void testFindById(){
        assertNotNull((eventService.findById(10L)));
    }

    @Test
    @Transactional
    public void testDelete(){
        eventService.delete(3L, user);
        assertNull((eventService.findById(3L)));
    }

    @Test
    @Transactional
    public void testUpdate(){
        Event event = eventService.findById(4L);
        event.setDescription("Descripcion actualiza");

        eventService.update(event, user);

        assertNotNull((eventService.findById(4L)));
        assertNotEquals("Ven a nuestra bodega a probar nuestros vinos de frabicacion nacional", eventService.findById(4L).getDescription());
        assertEquals("Descripcion actualiza", eventService.findById(4L).getDescription());
    }

    @Test
    @Transactional
    public void testFindByOrganizer(){
        assertNotNull((eventService.findByOrganizerId(1L, Pageable.ofSize(1))));
    }
    
}
