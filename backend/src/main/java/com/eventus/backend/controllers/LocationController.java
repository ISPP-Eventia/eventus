package com.eventus.backend.controllers;

import com.eventus.backend.models.Location;
import com.eventus.backend.models.User;
import com.eventus.backend.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LocationController extends ValidationController{

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService){
        this.locationService=locationService;
    }

    @GetMapping("/locations")
    @ResponseStatus(HttpStatus.OK)
    public List<Location> getLocations(@RequestParam(defaultValue = "0") Integer numPag){
        return this.locationService.findAll(PageRequest.of(numPag,20));
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

    @GetMapping("/user/locations")
    public ResponseEntity<List<Location>> getLocationsByUser(@AuthenticationPrincipal User owner, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.locationService.findByOwnerId(owner.getId(), PageRequest.of(numPag, 20)));
    }

    @PostMapping("/locations")
    public ResponseEntity<Object> createLocation(@Valid @RequestBody Location location,@AuthenticationPrincipal User user){
        try{
            locationService.create(location,user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
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

    @PutMapping("/locations/{id}")
    public ResponseEntity<Object> updateLocation(@Valid @RequestBody Location location, @PathVariable Long id) {
        try {
            this.locationService.update(location, id);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
}