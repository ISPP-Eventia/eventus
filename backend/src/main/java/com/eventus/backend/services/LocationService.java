package com.eventus.backend.services;

import java.util.List;

import com.eventus.backend.models.Location;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService{

    private final LocationRepository locationRepository;
    
    @Autowired
    public LocationService(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    @Override
    public void create(Location location,User owner) {
        if(owner != null){
            location.setOwner(owner);
        }

        locationRepository.save(location);
    }

    @Override
    public void delete(Location location) {
        locationRepository.delete(location);
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public List<Location> findAll(Pageable p) {
        return locationRepository.findAll(p);
    }

    @Override
    public Location findById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Location> findByOwnerId(Long ownerId, Pageable p) {
        return locationRepository.findByOwnerId(ownerId,p);
    }

    @Override
    public void save(Location location) {
        locationRepository.save(location);
    }

    @Override
    public void update(Location params, Long locationId) {
        Location location = locationRepository.findById(locationId).orElse(null);
        if (location != null) {
            location.setDescription(params.getDescription());
            location.setCoordinates(params.getCoordinates());
            location.setName(params.getName());
            location.setPrice(params.getPrice());
            locationRepository.save(location);
        }
    }
    
}
