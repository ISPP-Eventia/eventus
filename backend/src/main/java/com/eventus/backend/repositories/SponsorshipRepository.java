package com.eventus.backend.repositories;

import java.util.List;

import com.eventus.backend.models.Sponsorship;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SponsorshipRepository extends CrudRepository<Sponsorship, Long> {
    
    List<Sponsorship> findAll(Pageable page);

    List<Sponsorship> findSponsorByEventId(Long id, Pageable p);
    List<Sponsorship> findSponsorByUserId(Long id, Pageable p);

    @Query("SELECT s FROM Sponsorship s WHERE s.event.id=:eventId AND s.isAccepted=:state")
    List<Sponsorship> findByEventAndState(@Param("eventId") Long eventId,@Param("state") boolean state, Pageable p);
    
}
