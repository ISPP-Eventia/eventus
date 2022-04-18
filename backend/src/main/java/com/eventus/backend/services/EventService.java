package com.eventus.backend.services;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.EventRepository;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public List<Event> findAllNotFinished(Pageable page) {
        return this.eventRepository.findByEndDateIsGreaterThan(LocalDateTime.now(),page);
    }

    @Override
    public Event save(Event event) {
        Validate.isTrue(event.getStartDate().isBefore(event.getEndDate()), "Start date and end date can not overlap");
        return this.eventRepository.save(event);
    }
    @Override
    public Event update(Event event,User user) {

        Validate.notNull(event.getId());
        Event oldEvent = this.eventRepository.findById(event.getId()).orElse(null);
        Validate.notNull(oldEvent, "This event does not exist");
        Validate.isTrue(oldEvent.getOrganizer().getId().equals(user.getId())||user.isAdmin(), "You can not update an event that you are not the organizer");
        Validate.isTrue(event.getStartDate().isBefore(event.getEndDate()), "Start date and end date can not overlap");

        oldEvent.setDescription(event.getDescription());
        oldEvent.setTitle(event.getTitle());
        oldEvent.setStartDate(event.getStartDate());
        oldEvent.setEndDate(event.getEndDate());
        oldEvent.setPrice(event.getPrice());
        return this.eventRepository.save(oldEvent);
    }

    @Override
    public void delete(Long id,User user) {
        Event event = this.eventRepository.findById(id).orElse(null);
        Validate.notNull(event, "Event not found");
        Validate.isTrue(event.getOrganizer().getId().equals(user.getId())||user.isAdmin(), "You can not delete an event that you are not the organizer");
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

    @Override
    public List<Event> findRecommendedEvents(User user) {
        List<Event> lastUserEvents = this.eventRepository.findByParticipationsUserIdEqualsAndEndDateIsGreaterThanOrderByStartDateAsc(user.getId(), LocalDateTime.now(),PageRequest.of(0,30));
        double maxRating = lastUserEvents.stream().mapToDouble(Event::getRating).max().orElse(0);
        double median = lastUserEvents.stream().mapToDouble(x-> (x.getRating()/maxRating)*0.2+x.getParticipations().size()*0.5+x.getSponsors().size()*0.3).average().orElse(0.0);
        return this.eventRepository.findDistinctByParticipationsUserIdIsNotAndEndDateIsGreaterThanOrderByStartDateAsc(user.getId(),LocalDateTime.now(),PageRequest.of(0,100)).stream().sorted(Comparator.comparing(x-> Math.abs(((x.getRating()/maxRating)*0.2+x.getParticipations().size()*0.5+x.getSponsors().size()*0.3)-median))).collect(Collectors.toList());

    }
    
    
}