package com.eventus.backend.services;


import java.util.List;

import com.eventus.backend.models.Event;
import com.eventus.backend.repositories.EventRepository;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService {


    private final EventRepository eventRepository;

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
        Validate.isTrue(event.getStartDate().isBefore(event.getEndDate()), "Start date and end date can not overlap");
        return this.eventRepository.save(event);
    }
    @Override
    public Event update(Event event,Long userId) {
        
        Validate.notNull(event.getId());
        Event oldEvent = this.eventRepository.findById(event.getId()).orElse(null);
        Validate.notNull(oldEvent, "This event does not exist");
        Validate.isTrue(event.getStartDate().isBefore(event.getEndDate()), "Start date and end date can not overlap");
        Validate.isTrue(oldEvent.getOrganizer().getId().equals(userId), "You can not update an event that you are not the organizer");

        oldEvent.setDescription(event.getDescription());
        oldEvent.setTitle(event.getTitle());
        oldEvent.setStartDate(event.getStartDate());
        oldEvent.setEndDate(event.getEndDate());
        oldEvent.setPrice(event.getPrice());
        return this.eventRepository.save(oldEvent);
    }

    @Override
    public void delete(Long id) {
        this.eventRepository.deleteById(id);
    }

    @Override
    public Event findById(Long id) {
        return this.eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> findByOrganizerId(Long id, Pageable pageable) {
        return this.eventRepository.findByOrganizerId(id,pageable);
    }

    
    
}