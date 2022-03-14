package com.eventus.backend.controllers;

import java.util.List;



import com.eventus.backend.models.Event;
import com.eventus.backend.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/events")
public class EventController {
    
    
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public List<Event> getEvents(@RequestParam Pageable page){
        return this.eventService.findAll(page);
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id){
        return this.eventService.findById(id);
    }


    @PostMapping("/")
    public Event createEvent(@RequestBody Event event){
        return this.eventService.save(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable Long id){
        this.eventService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    } 

}
