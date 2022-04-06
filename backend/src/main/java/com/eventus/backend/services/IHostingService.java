package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.User;
import com.stripe.exception.StripeException;

import com.eventus.backend.models.User;
import org.springframework.data.domain.Pageable;

public interface IHostingService {
    
    public void save(Hosting hosting);
    public void create(Map<String,String> params);
    public Hosting findById(Long id);
    public List<Hosting> findAll(Pageable p);
    public void delete(Hosting hosting);
    public void deleteById(Long id);

    public void resolveHosting(boolean b, Long id,User user) throws StripeException;
    public List<Hosting> findByEventId(Long eventId, Pageable p, User user);
    List<Hosting> findByLocationId(Long locationId, Pageable p,User user);
    public List<Hosting>findByEventAndState(Long eventId,String state, Pageable p);
    Hosting findHostingByEventIdAndLocationId(Long eventId,Long locationId);
}
