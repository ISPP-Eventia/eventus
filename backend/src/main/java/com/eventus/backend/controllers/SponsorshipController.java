package com.eventus.backend.controllers;

import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.services.SponsorshipService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SponsorshipController {
    private final SponsorshipService sponsorService;

    @Autowired
    public SponsorshipController(SponsorshipService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping("/sponsorships")
    @ResponseStatus(HttpStatus.OK)
    public List<Sponsorship> getSponsors(@RequestParam(defaultValue = "0") Integer page) {
        return this.sponsorService.findAll(PageRequest.of(page, 20));
    }

    @GetMapping("/sponsorships/{id}")
    public ResponseEntity<Sponsorship> getSponsorById(@PathVariable Long id) {
        Sponsorship sponsor = this.sponsorService.findSponsorById(id);
        if (sponsor != null) {
            return ResponseEntity.ok(sponsor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{userId}/sponsorships")
    public ResponseEntity<List<Sponsorship>> getSponsorByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(this.sponsorService.findSponsorByUserId(userId, PageRequest.of(page, 20)));
    }

    @GetMapping("/events/{eventId}/sponsorships")
    public ResponseEntity<List<Sponsorship>> getSponsorByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(this.sponsorService.findSponsorByEventId(eventId, PageRequest.of(page, 20)));
    }

    @PostMapping("/sponsorships")
    public ResponseEntity<Sponsorship> createSponsor(@RequestBody Map<String, String> params) {
        try {
            sponsorService.create(params);
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/sponsorships/{id}")
    public ResponseEntity<String> deleteSponsor(@PathVariable Long id) {
        try {
            this.sponsorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/sponsorships/{id}")
    public ResponseEntity<Sponsorship> updateSponsor(@RequestBody Map<String, String> params, @PathVariable Long id) {
        try {
            this.sponsorService.update(params, id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
            
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/sponsorships/{id}")
    public ResponseEntity<Sponsorship> resolveSponsorship(@RequestBody boolean isAccepted, @PathVariable Long id) {
        try {
            this.sponsorService.resolveSponsorship(isAccepted, id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sponsorships/event/{id}/{state}")
    public ResponseEntity<List<Sponsorship>> getSponsorshipByEventAndState(@RequestParam(defaultValue = "0") Integer page, @PathVariable("state") String state, @PathVariable("id") Long eventId) {
        try {
            List<Sponsorship> result =
                    this.sponsorService.findByEventAndState(eventId, state, PageRequest.of(page, 20));
            if (result == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
