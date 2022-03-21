package com.eventus.backend.services;


import java.util.List;
import java.util.Optional;

import com.eventus.backend.models.Event;
import com.eventus.backend.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService {


    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepo){
        this.eventRepository = eventRepo;
    }

    @Override
    public List<Event> findAll(Pageable page) {
        return this.eventRepository.findAll(page);
    }

    @Override
    public Event save(Event event) {
        return this.eventRepository.save(event);
    }

    @Override
    public void delete(Long id) {
        this.eventRepository.deleteById(id);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return this.eventRepository.findById(id);
    }



    
    
}