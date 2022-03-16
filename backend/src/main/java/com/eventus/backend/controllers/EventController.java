package com.eventus.backend.controllers;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    
    
    private EventService eventService;
    private UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/events")
    public List<Event> getEvents(@RequestParam(defaultValue = "0") Integer numPag){
        return this.eventService.findAll(PageRequest.of(numPag, 20));
    }

    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable Long id){
        return this.eventService.findById(id);
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<String> updateEvent(@RequestBody Map<String, String> event,@PathVariable Long id){
        try {
            User user = this.userService.findUserById(Long.valueOf(event.get("organizerId"))).orElse(null);
            if(user!=null) {
                Event eventToUpdate = this.eventService.findById(id);
                if (eventToUpdate==null){
                    return ResponseEntity.notFound().build();
                }
                eventToUpdate.setDescription(event.get("description"));
                eventToUpdate.setPrice(Double.valueOf(event.get("price")));
                eventToUpdate.setTitle(event.get("title"));
                eventToUpdate.setOrganizer(user);
                this.eventService.save(eventToUpdate);
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(DataAccessException|NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/events")
    public ResponseEntity<String> createEvent(@RequestBody Map<String, String> event){
    	try {
    		User user = this.userService.findUserById(Long.valueOf(event.get("organizerId"))).orElse(null);
    		if(user!=null) {
    			Event saveEvent = new Event();
    			saveEvent.setDescription(event.get("description"));
    			saveEvent.setPrice(Double.valueOf(event.get("price")));
    			saveEvent.setTitle(event.get("title"));
    			saveEvent.setOrganizer(user);
    			this.eventService.save(saveEvent);
    		}
    		return ResponseEntity.status(HttpStatus.CREATED).build();
    	} catch(DataAccessException|NullPointerException e) {
    		return ResponseEntity.badRequest().build();
    	}
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id){
        this.eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    } 

}
