package com.eventus.backend.controllers;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class ParticipationController {

    private final ParticipationService participationService;

    private final UserService userService;

    private final EventService eventService;

    @Autowired
    public ParticipationController(ParticipationService participationService,UserService userService,EventService eventService) {
        this.participationService = participationService;
        this.userService = userService;
        this.eventService=eventService;
    }

    @GetMapping("/participations")
    @ResponseStatus(HttpStatus.OK)
    public List<Participation> getParticipations(@RequestParam(defaultValue = "0") Integer numPag) {
        return this.participationService.findAllParticipation(PageRequest.of(numPag,20));
    }

    @GetMapping("/participations/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long id) {
        Participation participation=this.participationService.findParticipationById(id).orElse(null);
        if(participation!=null){
            return ResponseEntity.ok(participation);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/user/event/{eventId}")
    public ResponseEntity<List<User>> getUsersByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
       return ResponseEntity.ok(this.participationService.findUsersByEventId(eventId, PageRequest.of(numPag,20)));
    }

    @GetMapping("/participations/user/{userId}")
    public ResponseEntity<List<Participation>>  getParticipationsByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findParticipationByUserId(userId,PageRequest.of(numPag,20)));
    }

    @GetMapping("/participations/event/{eventId}")
    public ResponseEntity<List<Participation>> getParticipationsByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findParticipationByEventId(eventId, PageRequest.of(numPag,20)));
    }

    @PostMapping("/participations")
    public ResponseEntity<String> createParticipation(@RequestBody Map<String,String> p) {
        try{
            Event event= this.eventService.findById(Long.valueOf(p.get("eventId")));
            User user = this.userService.findUserById(1L).orElse(null);

            if(user!=null&&event!=null){
                this.participationService.saveParticipation(event,user);
            }else{
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(DataAccessException|NullPointerException e){
            return ResponseEntity.badRequest().build();
        }


    }

    @DeleteMapping("/participations/{id}")
    public ResponseEntity<String> deleteParticipation(@PathVariable Long id) {
        this.participationService.deleteParticipation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}