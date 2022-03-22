package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Location;

import org.springframework.data.domain.Pageable;

public interface ILocationService {
    
    public Location save(Location sponsor);
    public Location create(Map<String,String> params);
    public Location findById(Long id);
    public List<Location> findAll(Pageable p);
    public void delete(Location location);
    public void deleteById(Long id);
    public List<Location> findByOwnerId(Long ownerId, Pageable p);
    public Location update(Map<String,String> params, Long locationId);
    
}
