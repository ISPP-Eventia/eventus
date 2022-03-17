package com.eventus.backend.repositories;

import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    List<Participation> findByUserIdEquals(Long id, Pageable pageable);

    List<Participation> findByEventIdEquals(Long id, Pageable pageable);

    @Query("SELECT p.user FROM Participation p WHERE p.event.id=?1")
    List<User> findUsersByEventId(Long eventId, Pageable pageable);

    List<Participation> findAll(Pageable pageable);
}
