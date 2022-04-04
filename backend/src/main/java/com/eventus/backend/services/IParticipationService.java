package com.eventus.backend.services;

import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IParticipationService {

    List<Participation> findAllParticipation(Pageable p);
    Participation findParticipationById(Long id);
    void deleteParticipation(Long id,User user);
    List<Participation> findParticipationByUserId(Long userId,Pageable p);
    List<Participation> findParticipationByEventId(Long eventId,Pageable p);
    List<User> findUsersByEventId(Long eventId, Pageable p);
    Participation findByUserIdEqualsAndEventIdEquals(Long userId,Long evenId);

}
