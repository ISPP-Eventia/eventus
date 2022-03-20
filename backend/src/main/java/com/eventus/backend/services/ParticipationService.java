package com.eventus.backend.services;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.ParticipationRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService implements IParticipationService{

    private ParticipationRepository partRepository;

    @Autowired
    public ParticipationService(ParticipationRepository partRepository) {
       this.partRepository = partRepository;
    }

    @Transactional
    public void saveParticipation(Event event, User user) throws DataAccessException {
        Participation participation=new Participation();
        participation.setTicket(RandomStringUtils.randomAlphanumeric(20));
        participation.setPrice(event.getPrice());
        participation.setEvent(event);
        participation.setUser(user);
        partRepository.save(participation);
    }

    public Participation findParticipationById(Long id) {
        return partRepository.findById(id).orElse(null);
    }

    public void deleteParticipation(Long id) {
        partRepository.deleteById(id);
    }

    public List<Participation> findParticipationByUserId(Long userId,Pageable p) {
        return partRepository.findByUserIdEquals(userId,p);
    }

    public List<Participation> findParticipationByEventId(Long eventId,Pageable p) {
        return partRepository.findByEventIdEquals(eventId,p);
    }

    public List<User> findUsersByEventId(Long eventId,Pageable p) {
        return partRepository.findUsersByEventId(eventId,p);
    }
    public List<Participation> findAllParticipation(Pageable p){
        return partRepository.findAll(p);
    }
}
