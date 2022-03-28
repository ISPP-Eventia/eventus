package com.eventus.backend.controllers;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.UserService;
import com.itextpdf.text.DocumentException;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.MalformedURLException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ParticipationController extends ValidationController{
    private final ParticipationService participationService;

    private final UserService userService;

    private final EventService eventService;

    @Autowired
    public ParticipationController(ParticipationService participationService, UserService userService, EventService eventService) {
        this.participationService = participationService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/participations")
    @ResponseStatus(HttpStatus.OK)
    public List<Participation> getParticipations(@RequestParam(defaultValue = "0") Integer numPag) {
        return this.participationService.findAllParticipation(PageRequest.of(numPag, 20));
    }

    @GetMapping("/participations/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long id) {
        Participation participation =
                this.participationService.findParticipationById(id);
        if (participation != null) {
            return ResponseEntity.ok(participation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/events/{eventId}/participants")
    public ResponseEntity<List<User>> getUsersByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findUsersByEventId(eventId, PageRequest.of(numPag, 20)));
    }

    @GetMapping("/users/{userId}/participations")
    public ResponseEntity<List<Participation>> getParticipationsByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findParticipationByUserId(userId, PageRequest.of(numPag, 20)));
    }

    @GetMapping("/events/{eventId}/participations")
    public ResponseEntity<List<Participation>> getParticipationsByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findParticipationByEventId(eventId, PageRequest.of(numPag, 20)));
    }

    @PostMapping("/participations")
    public ResponseEntity<Participation> createParticipation(@RequestBody Map<String, String> p, @AuthenticationPrincipal User user) throws MalformedURLException, DocumentException, IOException {
    	try {
            Event event = this.eventService.findById(Long.valueOf(p.get("eventId")));
            if (user != null && event != null) {
                Participation participation = this.participationService.findByUserIdEqualsAndEventIdEquals(user.getId(), event.getId());

                if (participation != null) {
                    return ResponseEntity.badRequest().build();
                }

                participation = this.participationService.createParticipationAndTicket(event, user);

                return new ResponseEntity<>(participation, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/participations/{id}")
    public ResponseEntity<String> deleteParticipation(@PathVariable Long id) {
        try {
            this.participationService.deleteParticipation(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/participation/{id}/ticket")
    public ResponseEntity<byte[]> generateTicket(@PathVariable Long id){
        ResponseEntity<byte[]> response = null;
        Participation participation=this.participationService.findParticipationById(id);
        if(participation!=null){
            try {
            	byte[] array = this.participationService.createTicketPDF(participation);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                String filename = "ticket.pdf";
                headers.setContentDispositionFormData(filename, filename);
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                response = new ResponseEntity<>(array, headers, HttpStatus.OK);
            } catch (DocumentException | IOException e) {
                response = ResponseEntity.badRequest().build();
            }
        } else {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }
}
