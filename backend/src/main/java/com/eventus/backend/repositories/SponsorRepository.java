package com.eventus.backend.repositories;

import java.util.List;

import com.eventus.backend.models.Sponsor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SponsorRepository extends CrudRepository<Sponsor, Long> {
    
    List<Sponsor> findAll(Pageable page);

    List<Sponsor> findSponsorByEventId(Long id, Pageable p);
    List<Sponsor> findSponsorByUserId(Long id, Pageable p);
    
}
