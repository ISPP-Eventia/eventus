package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Sponsorship;

import com.eventus.backend.models.User;
import com.stripe.exception.StripeException;

import org.springframework.data.domain.Pageable;

public interface ISponsorshipService {
    

    public void save(Sponsorship sponsor);
    public void create(Map<String,String> params, User user);
    public Sponsorship findSponsorById(Long id);
    public List<Sponsorship> findAll(Pageable p);
    public void delete(Sponsorship sponsor);
    public void deleteById(Long id);
    public List<Sponsorship> findSponsorByUserId(Long userId, Pageable p);
    public List<Sponsorship> findSponsorByEventId(Long eventId, Pageable p,Long userId);
    public void update(Map<String,String> params, Long sponorId);
    public void resolveSponsorship(boolean b, Long id, User user) throws StripeException;
    public List<Sponsorship>findByEventAndState(Long eventId,String state, Pageable p);
    
    
}
