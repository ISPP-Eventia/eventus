package com.eventus.backend.repositories;

import java.util.List;

import com.eventus.backend.models.Sponsorship;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SponsorRepository extends CrudRepository<Sponsorship, Long> {
    
    List<Sponsorship> findAll(Pageable page);

    List<Sponsorship> findSponsorByEventId(Long id, Pageable p);
    List<Sponsorship> findSponsorByUserId(Long id, Pageable p);
    
}
