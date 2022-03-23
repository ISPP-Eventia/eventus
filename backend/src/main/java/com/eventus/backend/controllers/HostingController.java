package com.eventus.backend.controllers;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.Location;
import com.eventus.backend.services.HostingService;
import com.eventus.backend.services.LocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HostingController {
    
    private final HostingService hostingService;
    private final LocationService locationService;
    
    @Autowired
    public HostingController(HostingService hostingService, LocationService locationService){
        this.hostingService = hostingService;
        this.locationService = locationService;
    }

    @GetMapping("/hostings")
    @ResponseStatus(HttpStatus.OK)
    public List<Hosting> getHostings(@RequestParam(defaultValue = "0") Integer numPag){
        return this.hostingService.findAll(PageRequest.of(numPag,20));
    }

    @GetMapping("/locations")
    @ResponseStatus(HttpStatus.OK)
    public List<Location> getLocations(@RequestParam(defaultValue = "0") Integer numPag){
        return this.locationService.findAll(PageRequest.of(numPag,20));
    }

    @GetMapping("/hostings/{id}")
    public ResponseEntity<Hosting> gethostingById(@PathVariable Long id) {
        Hosting hosting =
                this.hostingService.findById(id);
        if (hosting != null) {
            return ResponseEntity.ok(hosting);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/locations/{locationId}/hostings")
    public ResponseEntity<List<Hosting>> getHostingsByLocation(@PathVariable Long locationId,  @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.hostingService.findByLocationId(locationId, PageRequest.of(numPag, 20)));

    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Location location =
                this.locationService.findById(id);
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/events/{eventId}/hostings")
    public ResponseEntity<List<Hosting>> getUsersByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.hostingService.findByEventId(eventId, PageRequest.of(numPag, 20)));
    }

    @GetMapping("/users/{ownerId}/locations")
    public ResponseEntity<List<Location>> getLocationsByUser(@PathVariable Long ownerId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.locationService.findByOwnerId(ownerId, PageRequest.of(numPag, 20)));
    }

    @PostMapping("/hostings")
    public ResponseEntity<Hosting> createHosting(@RequestBody Map<String,String> params){
        try{
            hostingService.create(params);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@Valid @RequestBody Location location){
        try{
            locationService.create(location);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/hostings/{id}")
    public ResponseEntity<String> deleteHosting(@PathVariable Long id){
        try {
            this.hostingService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id){
        try {
            this.locationService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/hostings/{id}")
    public ResponseEntity<Hosting> updateSponsor(@RequestBody Map<String, String> params, @PathVariable Long id) {
        try {
            this.hostingService.update(params, id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
            
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> updateLocation(@RequestBody Map<String, String> params, @PathVariable Long id) {
        try {
            this.locationService.update(params, id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
            
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/events/{id}/hostings/{state}")
    public ResponseEntity<List<Hosting>> getHostingsByEventAndState(@RequestParam(defaultValue = "0") Integer page, @PathVariable("state") String state, @PathVariable("id") Long eventId) {
        try {
            List<Hosting> result =
                    this.hostingService.findByEventAndState(eventId, state, PageRequest.of(page, 20));
            if (result == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/hostings/{id}")
    public ResponseEntity<Hosting> resolveHosting(@RequestBody Map<String, String> params, @PathVariable Long id) {
        try {
            boolean isAccepted = "true".equals(params.get("isAccepted"));

            this.hostingService.resolveHosting(isAccepted, id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }





    
}
