package com.eventus.backend.services;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Location;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.LocationRepository;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService{

    private LocationRepository locationRepository;
    private UserService userService;
    
    @Autowired
    public LocationService(LocationRepository locationRepository, UserService userService){
        this.locationRepository = locationRepository;
        this.userService = userService;
    }

    @Override
    public void create(Map<String, String> params) {
        Location entity = new Location();
        User owner = userService.findUserById(Long.valueOf(params.get("owner")));
        Validate.isTrue(owner != null);
        entity.setOwner(owner);
        entity.setName(params.get("name"));
        entity.setDescription(params.get("description"));
        entity.setLocation(params.get("location"));
        entity.setPrice(Double.valueOf(params.get("price")));

        locationRepository.save(entity);
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
        Validate.isTrue(location.getOwner() != null);
        locationRepository.save(location);
    }

    @Override
    public void update(Map<String, String> params, Long locationId) {
        Location location = locationRepository.findById(locationId).orElse(null);
        Validate.isTrue(location!=null);
        location.setDescription(params.get("description"));
        location.setLocation(params.get("location"));
        location.setName(params.get("name"));
        location.setPrice(Double.valueOf(params.get("price")));
        locationRepository.save(location);
    }
    
}
