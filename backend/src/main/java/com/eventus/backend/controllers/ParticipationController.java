package com.eventus.backend.controllers;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.ParticipationService;
import com.eventus.backend.services.StripeService;
import com.itextpdf.text.DocumentException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethodCollection;

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
    private final EventService eventService;
    private final StripeService stripeService;

    @Autowired
    public ParticipationController(ParticipationService participationService, EventService eventService,StripeService stripeService) {
        this.participationService = participationService;
        this.eventService = eventService;
        this.stripeService = stripeService;
    }

    @GetMapping("/participations/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long id,@AuthenticationPrincipal User user) {
        Participation participation =
                this.participationService.findParticipationById(id);
        if (participation != null) {
            if(user.isAdmin() || participation.getUser().getId().equals(user.getId())) {
                return ResponseEntity.ok(participation);
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/events/{eventId}/participants")
    public ResponseEntity<List<User>> getUsersByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findUsersByEventId(eventId, PageRequest.of(numPag, 20000)));
    }

    @GetMapping("/user/participations")
    public ResponseEntity<List<Participation>> getParticipationsByUser(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") Integer numPag) {
        if(user.isAdmin()) {
            return ResponseEntity.ok(this.participationService.findAllParticipation(PageRequest.of(numPag, 20000)));
        }else{
            return ResponseEntity.ok(this.participationService.findParticipationByUserId(user.getId(), PageRequest.of(numPag, 20000)));
        }
    }

    @GetMapping("/events/{eventId}/participations")
    public ResponseEntity<List<Participation>> getParticipationsByEvent(@PathVariable Long eventId, @RequestParam(defaultValue = "0") Integer numPag) {
        return ResponseEntity.ok(this.participationService.findParticipationByEventId(eventId, PageRequest.of(numPag, 20000)));
    }

    @PostMapping("/participations")
    public ResponseEntity<Participation> createParticipation(@RequestBody Map<String, String> p, @AuthenticationPrincipal User user) throws MalformedURLException, DocumentException, IOException, StripeException {
    	try {
            Event event = this.eventService.findById(Long.valueOf(p.get("eventId")));
            if (user != null && event != null) {
                Participation participation = this.participationService.findByUserIdEqualsAndEventIdEquals(user.getId(), event.getId());

                if (participation != null) {
                    return ResponseEntity.badRequest().build();
                }
                PaymentMethodCollection paymentMethods = stripeService.getPaymentMethods(user);
                if(paymentMethods.getData().isEmpty()){
                    return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
                }else{
                    participation = this.participationService.createParticipationAndTicket(event, user);
                }
                return new ResponseEntity<>(participation, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/participations/{id}")
    public ResponseEntity<Map<String,String>> deleteParticipation(@PathVariable Long id,@AuthenticationPrincipal User user) {
        try {
            this.participationService.deleteParticipation(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/participation/{id}/ticket")
    public ResponseEntity<byte[]> generateTicket(@PathVariable Long id, @AuthenticationPrincipal User user) {
        ResponseEntity<byte[]> response = null;
        Participation participation=this.participationService.findParticipationById(id);
        if(participation!=null&&(participation.getUser().getId().equals(user.getId())||user.isAdmin())) {
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
