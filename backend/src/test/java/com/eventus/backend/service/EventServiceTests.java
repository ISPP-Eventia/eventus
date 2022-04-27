package com.eventus.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.EventRepository;
import com.eventus.backend.services.EventService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTests {

    @Autowired
    protected EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    Event event;
    @Mock
    User user;

    @Before
    public void setUp(){ 
        eventService = new EventService(eventRepository);

        when(user.getId()).thenReturn(1L);
        when(event.getId()).thenReturn(1L);
        when(event.getOrganizer()).thenReturn(user);
        when(event.getOrganizer()).thenReturn(user);
        when(event.getStartDate()).thenReturn(LocalDateTime.now());
        when(event.getEndDate()).thenReturn(LocalDateTime.now().plusHours(5));
    }


    @Test
    public void testSaveEvent(){
        when(eventRepository.save(event)).thenReturn(event);
        
        Event event = eventService.save(this.event);

        assertTrue(event != null);
        assertEquals(this.event, event);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testFindAllEvents(){
        List<Event> events = new ArrayList<>();
        events.add(event);

        when(eventRepository.findAll(Pageable.ofSize(1))).thenReturn(events);
        
        assertNotNull(eventService.findAll(Pageable.ofSize(1)));
        assertEquals(1, eventService.findAll(Pageable.ofSize(1)).size());
    }

    @Test
    public void testFindAllNotFinishedPositive(){
        List<Event> eventsResponse = new ArrayList<>();
        eventsResponse.add(event);

        when(eventRepository.findByEndDateIsGreaterThan(any(LocalDateTime.class), eq(PageRequest.of(0, 10)))).thenReturn(eventsResponse);
        
        List<Event> events = eventService.findAllNotFinished(PageRequest.of(0, 10));
        assertNotNull(events);
        assertEquals(eventsResponse, events);
    }

    @Test
    public void testFindById(){
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        
        assertNotNull((eventService.findById(1L)));
    }

    @Test
    public void testDelete(){
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        eventService.delete(1L, user);

        verify(eventRepository, times(1)).deleteById(event.getId());
    }

    @Test
    public void testUpdate(){
        Event event1 = new Event();
		event1.setId(1L);
		event1.setTitle("New Title");
		event1.setDescription("Event 1 for testing");
        event1.setStartDate(LocalDateTime.now().plusDays(1));
        event1.setEndDate(LocalDateTime.now().plusDays(2));
		event1.setPrice(2.00);
		event1.setOrganizer(user);
		
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        eventService.update(event1, user);

        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testFindByOrganizer(){
        List<Event> eventsResponse = new ArrayList<>();
        eventsResponse.add(event);

        when(this.eventRepository.findByOrganizerId(user.getId(), PageRequest.of(0, 10))).thenReturn(eventsResponse);
        
        assertNotNull((eventService.findByOrganizerId(1L, PageRequest.of(0, 10))));
        assertEquals(eventsResponse, eventService.findByOrganizerId(1L, PageRequest.of(0, 10)));
    }
    
}
