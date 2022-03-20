package com.eventus.backend.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eventus.backend.models.Sponsorship;

import org.springframework.data.domain.Pageable;

public interface ISponsorService {
    

    public Sponsorship save(Sponsorship sponsor);
    public Sponsorship create(Map<String,String> params);
    public Optional<Sponsorship> findSponsorById(Long id);
    public List<Sponsorship> findAll(Pageable p);
    public void delete(Sponsorship sponsor);
    public void deleteById(Long id);
    public List<Sponsorship> findSponsorByUserId(Long userId, Pageable p);
    public List<Sponsorship> findSponsorByEventId(Long eventId, Pageable p);
    public Sponsorship update(Map<String,String> params, Long sponorId);
    
    
}
