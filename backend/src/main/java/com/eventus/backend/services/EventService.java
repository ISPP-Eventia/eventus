package com.eventus.backend.services;


import java.util.ArrayList;
import java.util.List;

import com.eventus.backend.models.Event;
import com.eventus.backend.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService {


    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepo){
        this.eventRepository = eventRepo;
    }

    @Override
    public List<Event> findAll() {
        return (List<Event>) this.eventRepository.findAll();
    }

    
    
}