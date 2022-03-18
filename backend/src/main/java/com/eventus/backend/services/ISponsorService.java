package com.eventus.backend.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eventus.backend.models.Sponsor;

import org.springframework.data.domain.Pageable;

public interface ISponsorService {
    

    public Sponsor save(Sponsor sponsor);
    public Sponsor create(Map<String,String> params);
    public Optional<Sponsor> findSponsorById(Long id);
    public List<Sponsor> findAll(Pageable p);
    public void delete(Sponsor sponsor);
    public void deleteById(Long id);
    public List<Sponsor> findSponsorByUserId(Long userId, Pageable p);
    public List<Sponsor> findSponsorByEventId(Long eventId, Pageable p);
    public Sponsor update(Map<String,String> params, Long sponorId);
    
    
}
