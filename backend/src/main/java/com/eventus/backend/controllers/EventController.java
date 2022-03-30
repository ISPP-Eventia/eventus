package com.eventus.backend.controllers;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;


import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EventController extends ValidationController{
  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/events")
  public List<Event> getEvents(@RequestParam(defaultValue = "0") Integer numPag) {
    return this.eventService.findAll(PageRequest.of(numPag, 20));
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable Long id) {
    Event event = this.eventService.findById(id);
    if (event != null) {
      return ResponseEntity.ok(event);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/events")
  public ResponseEntity<String> updateEvent(@Valid @RequestBody Event event) {
    try {
      Validate.notNull(event.getId());
      this.eventService.save(event);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (DataAccessException | NullPointerException e) {
      return ResponseEntity.badRequest().build();
    }catch(IllegalArgumentException e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/events")
  public ResponseEntity<String> createEvent(@Valid @RequestBody Event event,@AuthenticationPrincipal User user) {
    try {
      Validate.isTrue(event.getStartDate().isBefore(event.getEndDate()), "Start date and end date can not overlap");
      if (user != null) {
        event.setId(null);
        event.setOrganizer(user);
        this.eventService.save(event);
      }
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (DataAccessException | NullPointerException  e) {
      return ResponseEntity.badRequest().build();
    }catch(IllegalArgumentException e){
      return ResponseEntity.badRequest().body("{ \"error\":\""+e.getMessage()+"\"}");
    }
  }

  @DeleteMapping("/events/{id}")
  public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
    try {
      this.eventService.delete(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (DataAccessException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/users/events")
  public ResponseEntity<List<Event>> getEventsByOrganizer(@AuthenticationPrincipal User owner, @RequestParam(defaultValue = "0") Integer numPag) {
    return ResponseEntity.ok(this.eventService.findByOrganizerId(owner.getId(), PageRequest.of(numPag, 20)));
  }
}
