package com.eventus.backend.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventus.backend.models.Event;
import com.eventus.backend.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/events")
public class EventController {
    
    
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public List<Event> getEvents(HttpServletRequest req, HttpServletResponse resp){
        return this.eventService.findAll();
    }

}
