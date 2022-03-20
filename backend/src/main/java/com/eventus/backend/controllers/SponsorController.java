package com.eventus.backend.controllers;

import java.util.List;
import java.util.Map;

import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.services.SponsorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SponsorController {
    
    private final SponsorService sponsorService;

    @Autowired
    public SponsorController(SponsorService sponsorService){
        this.sponsorService = sponsorService;
    }

    @GetMapping("/sponsors")
    @ResponseStatus(HttpStatus.OK)
    public List<Sponsorship> getSponsors(@RequestParam(defaultValue = "0") Integer page){
        return this.sponsorService.findAll(PageRequest.of(page, 20));
    }

    @GetMapping("/sponsors/{id}")
    public ResponseEntity<Sponsorship> getSponsorById(@PathVariable Long id){
        Sponsorship sponsor = this.sponsorService.findSponsorById(id).orElse(null);
        if(sponsor != null){
            return ResponseEntity.ok(sponsor);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("sponsors/user/{userId}")
    public ResponseEntity<List<Sponsorship>> getSponsorByUser(@PathVariable Long userId,@RequestParam(defaultValue = "0") Integer page){
        return ResponseEntity.ok(this.sponsorService.findSponsorByUserId(userId, PageRequest.of(page, 20)));
    }

    @GetMapping("sponsors/event/{eventId}")
    public ResponseEntity<List<Sponsorship>> getSponsorByEvent(@PathVariable Long eventId,@RequestParam(defaultValue = "0") Integer page){
        return ResponseEntity.ok(this.sponsorService.findSponsorByEventId(eventId, PageRequest.of(page, 20)));
    }

    @PostMapping("/sponsors")
    public ResponseEntity<Sponsorship> createSponsor(@RequestBody Map<String,String> params){
        try{
            Sponsorship sponsor = this.sponsorService.create(params);
            if(sponsor == null){
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(sponsor);
        }catch(DataAccessException|NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/sponsors/{id}")
    public ResponseEntity<String> deleteSponsor(@PathVariable Long id){
        try{
            this.sponsorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    @PutMapping("/sponsors/{id}")
    public ResponseEntity<Sponsorship> updateSponsor(@RequestBody Map<String,String> params, @PathVariable Long id){
        try{
            Sponsorship newSponsor = this.sponsorService.update(params, id);
            if(newSponsor != null){
                return ResponseEntity.status(HttpStatus.CREATED).body(newSponsor);
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch(DataAccessException|NullPointerException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
