package com.eventus.backend.services;

import java.util.List;

import com.eventus.backend.models.Location;

import com.eventus.backend.models.User;
import org.springframework.data.domain.Pageable;

public interface ILocationService {
    
    public void save(Location sponsor);
    public void create(Location location, User owner);
    public Location findById(Long id);
    public List<Location> findAll(Pageable p);
    public void delete(Location location);
    public void deleteById(Long id);
    public List<Location> findByOwnerId(Long ownerId, Pageable p);
    public void update(Location location, Long locationId);
    
}
