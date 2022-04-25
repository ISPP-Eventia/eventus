package com.eventus.backend.controllers;

import com.eventus.backend.models.Location;
import com.eventus.backend.models.User;
import com.eventus.backend.services.LocationService;
import com.eventus.backend.services.MediaService;

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
    private final MediaService mediaService;

    @Autowired
    public LocationController(LocationService locationService, MediaService mediaService){
        this.locationService=locationService;
        this.mediaService = mediaService;
    }



    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocations(@RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.locationService.findAll(PageRequest.of(numPag,20000)));
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
        if(owner.isAdmin()){
            return ResponseEntity.ok(this.locationService.findAll(PageRequest.of(numPag,20000)));
        }else{
            return ResponseEntity.ok(this.locationService.findByOwnerId(owner.getId(), PageRequest.of(numPag,20000)));
        }

    }


    @PostMapping("/locations")
    public ResponseEntity<Object> createLocation(@Valid @RequestBody Location location,@AuthenticationPrincipal User user,@RequestParam(name="mediaIds") List<Long> mediaIds){
        try{
            locationService.create(location,user);
            this.mediaService.parseLocationMediaIds(mediaIds, location, user);
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Object> deleteLocation(@PathVariable Long id, @AuthenticationPrincipal User user) {
        try {
            this.locationService.deleteById(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Object> updateLocation(@Valid @RequestBody Location location, @PathVariable Long id, @AuthenticationPrincipal User user, @RequestParam(name="media") List<Long> mediaIds) {
        try {
            // location.setMedia(this.mediaService.parseMediaIds(mediaIds));
            this.locationService.update(location, id,user);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
}
