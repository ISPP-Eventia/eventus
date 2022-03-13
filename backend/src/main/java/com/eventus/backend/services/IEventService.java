package com.eventus.backend.services;

import java.util.List;

import com.eventus.backend.models.Event;

public interface IEventService {
    
    public List<Event> findAll();

}
