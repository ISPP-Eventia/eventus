package com.eventus.backend.repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.eventus.backend.models.Event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event,Long>{
    
    List<Event> findAll(Pageable page);

    List<Event> findByEndDateIsGreaterThan(LocalDateTime endDate,Pageable page);

    List<Event> findByOrganizerId(Long id, Pageable page);

    List<Event> findByParticipationsUserIdEqualsAndEndDateIsGreaterThanOrderByStartDateAsc(Long id, LocalDateTime endDate,Pageable page);

    List<Event> findDistinctByParticipationsUserIdIsNotAndEndDateIsGreaterThanOrderByStartDateAsc(Long id, LocalDateTime endDate, Pageable pageable);




}
