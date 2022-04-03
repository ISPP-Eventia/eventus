package com.eventus.backend.controllers;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Hosting;

import com.eventus.backend.models.User;
import com.eventus.backend.services.HostingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HostingController extends ValidationController{
    
    private final HostingService hostingService;
    
    @Autowired
    public HostingController(HostingService hostingService){
        this.hostingService = hostingService;
    }

    @GetMapping("/hostings")
    @ResponseStatus(HttpStatus.OK)
    public List<Hosting> getHostings(@RequestParam(defaultValue = "0") Integer numPag){
        return this.hostingService.findAll(PageRequest.of(numPag,20000));
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
    public ResponseEntity<List<Hosting>> getHostingsByLocation(@PathVariable Long locationId,  @RequestParam(defaultValue = "0") Integer numPag,@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.hostingService.findByLocationId(locationId, PageRequest.of(numPag, 20000), user));

    }

    @GetMapping("/events/{eventId}/hostings")
    public ResponseEntity<Object> getHostingsByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag,@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok().body(this.hostingService.findByEventId(eventId, PageRequest.of(numPag, 20000), user));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }

    }

    @PostMapping("/hostings")
    public ResponseEntity<Object> createHosting(@RequestBody Map<String,String> params){
        try{
            hostingService.create(params);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
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

    @GetMapping("/events/{id}/hostings/{state}")
    public ResponseEntity<List<Hosting>> getHostingsByEventAndState(@RequestParam(defaultValue = "0") Integer page, @PathVariable("state") String state, @PathVariable("id") Long eventId) {
        try {
            List<Hosting> result =
                    this.hostingService.findByEventAndState(eventId, state, PageRequest.of(page, 20000));
            if (result == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/hostings/{id}")
    public ResponseEntity<Map<String,String>> resolveHosting(@RequestBody Map<String, String> params, @PathVariable Long id,@AuthenticationPrincipal User user) {
        try {
            boolean isAccepted = "true".equals(params.get("isAccepted"));

            this.hostingService.resolveHosting(isAccepted, id,user);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
    
}
