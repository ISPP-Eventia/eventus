package com.eventus.backend.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Image;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {

	@Autowired
	protected EventService eventService;
	
	@Autowired
	protected UserService userService;
	
	private User user1 = new User();
	
	@Before
	public void init() {
		List<Image> images = new ArrayList<Image>();
		
        Image image1 = new Image();
        image1.setId(1L);
        image1.setTitle("Image 1");
        image1.setPath("Path");
        image1.setUploadDate(LocalDate.now());
        images.add(image1);
        
        Image image2 = new Image();
        image2.setId(2L);
        image2.setTitle("Image 2");
        image2.setPath("Path2");
        image2.setUploadDate(LocalDate.now());     
        images.add(image2);
		
        user1.setId(1L);
        user1.setFirstName("Pepe");
        user1.setLastName("Gonzalez");
        user1.setImage(image2);
        userService.saveUser(user1);
        
		Event event1 = new Event();
		event1.setId(1L);
		event1.setTitle("Event 1");
		event1.setDescription("Event 1 for testing");
		event1.setPrice(2.00);
		event1.setOrganizerId(user1);
		event1.setImages(images);
		eventService.save(event1);
		
		Event event2 = new Event();
		event2.setId(2L);
		event2.setTitle("Event 2");
		event2.setDescription("Event 2 for testing");
		event2.setPrice(4.00);
		eventService.save(event1);
	}
	
	@Test
	@Transactional
	public void shouldCreateEvent() {
		Pageable page = PageRequest.of(0,2);
		assertEquals(2, eventService.findAll(page).size());
	}
	
	@Test
	@Transactional
	public void countImagesInEvent() {
		List<Event> events = eventService.findAll(PageRequest.of(0,2));
		assertEquals(2, eventService.findById(events.get(0).getId()).getImages().size());
	}
	
	@Test
	@Transactional
	public void relationshipBetweenUserAndEvent() {
		List<Event> events = eventService.findAll(PageRequest.of(0,2));
		Event e = events.get(0);
		User u = eventService.findById(e.getId()).getOrganizerId();
		Long id = user1.getId();
		assertEquals(id, u.getId());
	}
	
	@Test
	@Transactional
	public void deleteEvent() {
		Pageable page = PageRequest.of(0,2);
		List<Event> events = eventService.findAll(page);
		eventService.delete(events.get(0).getId());
		assertFalse(eventService.findAll(page).contains(events.get(0)));		
	}
	
}
