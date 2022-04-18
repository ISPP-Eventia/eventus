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
        Validate.isTrue(event.getStartDate().isBefore(event.getEndDate()), "La fecha de inicio no puede ser mayor a la fecha de fin");
        return this.eventRepository.save(event);
    }
    @Override
    public Event update(Event event,User user) {

        Validate.isTrue(event.getId()!=null, "El evento no tiene id");
        Event oldEvent = this.eventRepository.findById(event.getId()).orElse(null);
        Validate.isTrue(oldEvent!=null, "Evento no encontrado");
        Validate.isTrue(oldEvent.getOrganizer().getId().equals(user.getId())||user.isAdmin(), "No puedes modificar un evento del que no eres el organizador");
        Validate.isTrue(event.getStartDate().isBefore(event.getEndDate()), "La fecha de inicio no puede ser mayor a la fecha de fin");

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
        Validate.isTrue(event!=null, "Evento no encontrado");
        Validate.isTrue(event.getOrganizer().getId().equals(user.getId())||user.isAdmin(), "No puedes eliminar un evento del que no eres el organizador");
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