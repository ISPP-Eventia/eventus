//package com.eventus.backend.controller;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeAll;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.eventus.backend.controllers.EventController;
//import com.eventus.backend.models.Event;
//import com.eventus.backend.models.Image;
//import com.eventus.backend.models.User;
//import com.eventus.backend.services.EventService;
//import com.eventus.backend.services.UserService;
//
//@WebMvcTest(controllers = EventController.class)
//public class EventControllerTests {
//	
//    @Autowired                           
//    private MockMvc mockMvc;  
//                                                 
//    @MockBean                           
//    private EventService eventService;
//    
//    @MockBean
//    private UserService userService;
//
//	@BeforeAll
//	public void init() {
//		List<Image> images = new ArrayList<Image>();
//		
//        Image image1 = new Image();
//        image1.setId(1L);
//        image1.setTitle("Image 1");
//        image1.setPath("Path");
//        image1.setUploadDate(LocalDate.now());
//        images.add(image1);
//        
//        Image image2 = new Image();
//        image2.setId(2L);
//        image2.setTitle("Image 2");
//        image2.setPath("Path2");
//        image2.setUploadDate(LocalDate.now());     
//        images.add(image2);
//		
//        User user1 = new User();
//        user1.setId(1L);
//        user1.setFirstName("Pepe");
//        user1.setLastName("Gonzalez");
//        user1.setImage(image2);
//        userService.saveUser(user1);
//        
//		Event event1 = new Event();
//		event1.setId(1L);
//		event1.setTitle("Event 1");
//		event1.setDescription("Event 1 for testing");
//		event1.setPrice(2.00);
//		event1.setOrganizerId(user1);
//		event1.setImages(images);
//		eventService.save(event1);
//		
//		Event event2 = new Event();
//		event2.setId(2L);
//		event2.setTitle("Event 2");
//		event2.setDescription("Event 2 for testing");
//		event2.setPrice(4.00);
//		eventService.save(event1);
//	}
//	
//	@Test
//	public void getAllEvents() {
//		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
//	}
//
//}
