package com.eventus.backend.repositories;

import com.eventus.backend.models.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    Page<Participation> findAll(Pageable pageable);
}
