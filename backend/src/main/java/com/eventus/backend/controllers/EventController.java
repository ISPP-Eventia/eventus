package com.eventus.backend.controllers;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.MediaService;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EventController extends ValidationController{
  private final EventService eventService;
  private final MediaService mediaService;

  @Autowired
  public EventController(EventService eventService, MediaService mediaService) {
    this.eventService = eventService;
    this.mediaService = mediaService;
  }

  @GetMapping("/events")
  public List<Event> getEvents(@RequestParam(defaultValue = "0") Integer numPag, @AuthenticationPrincipal User user) {
    if(user.isAdmin()){
      return this.eventService.findAll(PageRequest.of(numPag, 20000));
    }else {
      return this.eventService.findAllNotFinished(PageRequest.of(numPag, 20000));
    }

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
  public ResponseEntity<Object> updateEvent(@Valid @RequestBody Event event, @RequestParam(name="mediaIds") List<Long> mediaIds,@AuthenticationPrincipal User user) {
    try {
      this.eventService.update(event,user);
      this.mediaService.parseEventMediaIds(mediaIds, event, user);

      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (DataAccessException | NullPointerException e) {
      return ResponseEntity.badRequest().build();
    } catch(IllegalArgumentException e){
      return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
    }
  }

  @PostMapping("/events")
  public ResponseEntity<Object> createEvent(@RequestParam(value =  "mediaIds") List<Long> mediaIds, @Valid @RequestBody Event event,@AuthenticationPrincipal User user ) {
    try {

      event.setId(null);
      event.setOrganizer(user);
      this.eventService.save(event);
      this.mediaService.parseEventMediaIds(mediaIds, event, user);

      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (DataAccessException | NullPointerException  e) {
      return ResponseEntity.badRequest().build();
    }catch(IllegalArgumentException e){
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @DeleteMapping("/events/{id}")
  public ResponseEntity<String> deleteEvent(@PathVariable Long id,@AuthenticationPrincipal User user) {
    try {
      this.eventService.delete(id,user);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (DataAccessException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/users/events")
  public ResponseEntity<List<Event>> getEventsByOrganizer(@AuthenticationPrincipal User owner, @RequestParam(defaultValue = "0") Integer numPag) {
    if(owner.isAdmin()){
      return ResponseEntity.ok(this.eventService.findAll(PageRequest.of(numPag, 20000)));
    }else{
      return ResponseEntity.ok(this.eventService.findByOrganizerId(owner.getId(), PageRequest.of(numPag, 20000)));
    }

  }
  @GetMapping("/events/recommend")
  public ResponseEntity<List<Event>> getRecommendedEvents(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(this.eventService.findRecommendedEventsByUser(user));
  }

  @GetMapping("/events/recommend/{eventId}")
  public ResponseEntity<List<Event>> getRecommendedEventsByEvent(@AuthenticationPrincipal User user,@PathVariable Long eventId) {
    Event event = this.eventService.findById(eventId);
    if(event!=null){
      return ResponseEntity.ok(this.eventService.findRecommendedByEvent(user,event));
    }else{
      return ResponseEntity.notFound().build();
    }

  }
}
