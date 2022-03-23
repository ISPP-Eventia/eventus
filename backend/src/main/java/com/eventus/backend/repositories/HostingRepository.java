package com.eventus.backend.repositories;

import java.util.List;
import java.util.Optional;

import com.eventus.backend.models.Hosting;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HostingRepository extends CrudRepository<Hosting, Long>{

    List<Hosting> findAll(Pageable p);

    List<Hosting> findByEventId(Long eventId, Pageable p);

    @Query("SELECT h FROM Hosting h JOIN h.location l WHERE l.id = :locationId")
    List<Hosting> findByLocationId(@Param("locationId") Long locationId, Pageable p);

    @Query("SELECT h FROM Hosting h WHERE h.event.id=:eventId AND h.isAccepted=:state")
    List<Hosting> findByEventAndState(@Param("eventId") Long eventId,@Param("state") boolean state, Pageable p);

    @Query("SELECT h FROM Hosting h WHERE h.event.id=:eventId AND h.location.id=:locationId")
    Optional<Hosting> findByEventAndLocation(@Param("eventId") Long eventId,@Param("locationId") Long locationId);
    
    
    
}
