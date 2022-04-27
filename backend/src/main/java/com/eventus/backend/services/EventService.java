package com.eventus.backend.services;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Tag;
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
    public List<Event> findRecommendedEventsByUser(User user) {
        List<Event> lastUserEvents = this.eventRepository.findByParticipationsUserIdEqualsOrderByStartDateAsc(user.getId(), PageRequest.of(0,30));
        List<Tag> topTags = lastUserEvents.stream().flatMap(e->e.getTags().stream()).collect(Collectors.groupingBy(x->x, Collectors.counting())).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(5).map(Map.Entry::getKey).collect(Collectors.toList());
        return findSimilarEventsByTag(user.getId(),topTags);

    }

    @Override
    public List<Event> findRecommendedByEvent(User user,Event event) {
        List<Event> res = findSimilarEventsByTag(user.getId(),new ArrayList<>(event.getTags()));
        res.remove(event);
        return res;

    }
    private Double getDiceCoefficient(List<Tag> tags1, List<Tag> tags2){
        Set<Tag> intersection = tags1.stream().filter(tags2::contains).collect(Collectors.toSet());
        int size=tags1.size()+tags2.size();
        if(size==0){
            return 0.0;
        }
        return (2.0*intersection.size())/size;
    }

    private List<Event> findSimilarEventsByTag(Long userId,List<Tag> tags) {
        List<Event> similarEvents = this.eventRepository.findDistinctByParticipationsUserIdIsNotAndEndDateIsGreaterThanOrderByStartDateAsc(userId,LocalDateTime.now(),PageRequest.of(0,100));
        Double maxPart= eventRepository.getMaxPartPrice();
        Double maxSpon= eventRepository.getMaxSponsorshipPrice();
        similarEvents.sort(Comparator.comparing(e->getDiceCoefficient(tags,new ArrayList<>(e.getTags()))+0.00001*getRealRating(e,maxPart,maxSpon),Comparator.reverseOrder()));
        return similarEvents.subList(0,Math.min(similarEvents.size(),10));
    }

    private Double getRealRating(Event event,Double maxPart,Double maxSpon){
        int numPart = event.getParticipations().size();
        int numSpons = event.getSponsors().size();
        double totalDineroParticipaciones = 0;
        if (numPart != 0) {
            totalDineroParticipaciones = event.getParticipations().stream().mapToDouble(p -> (p.getPrice()*10)/maxPart).average().orElse(0.0);
        }
        double totalDineroSponsors = 0;
        if (numSpons != 0) {
            totalDineroSponsors = event.getSponsors().stream().mapToDouble(p -> (p.getQuantity()*10)/maxSpon).average().orElse(0.0);
        }

        return numPart * 0.4 + numSpons * 0.3 + ((totalDineroParticipaciones + totalDineroSponsors)/2) * 0.3;
    }
}