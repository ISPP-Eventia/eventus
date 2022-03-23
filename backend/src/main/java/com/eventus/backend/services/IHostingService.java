package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Hosting;

import org.springframework.data.domain.Pageable;

public interface IHostingService {
    
    public void save(Hosting hosting);
    public void create(Map<String,String> params);
    public Hosting findById(Long id);
    public List<Hosting> findAll(Pageable p);
    public void delete(Hosting hosting);
    public void deleteById(Long id);
    public List<Hosting> findByEventId(Long eventId, Pageable p);
    public List<Hosting> findByLocationId(Long locationId, Pageable p);
    public void update(Map<String,String> params, Long hostingId);
    public void resolveHosting(boolean b, Long id);
    public List<Hosting>findByEventAndState(Long eventId,String state, Pageable p);
    public void resolveSponsorship(boolean isAccepted, Long id);
    Hosting findHostingByEventIdAndLocationId(Long eventId,Long locationId);
}
