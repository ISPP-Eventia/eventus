package com.eventus.backend.services;

import java.util.List;

import com.eventus.backend.models.Event;

import com.eventus.backend.models.User;
import org.springframework.data.domain.Pageable;

public interface IEventService {
    
    public List<Event> findAll(Pageable page);

    List<Event> findAllNotFinished(Pageable page);
    public Event findById(Long id);
    public Event save(Event event);
    public void delete(Long id, User user);
    Event update(Event event, User user);

    List<Event> findByOrganizerId(Long id, Pageable pageable);

    List<Event> findRecommendedEventsByUser(User user);

    List<Event> findRecommendedByEvent(User user,Event event);
}
