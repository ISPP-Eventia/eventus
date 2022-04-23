package com.eventus.backend.controllers;

import com.eventus.backend.models.Tag;
import com.eventus.backend.models.User;
import com.eventus.backend.services.TagService;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TagController extends ValidationController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags")
    public ResponseEntity<Object> createTag(@Valid @RequestBody Tag tag, @AuthenticationPrincipal User user) {
        try {
            tagService.save(tag);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/tags")
    public ResponseEntity<Object> getTags(@RequestParam(defaultValue = "") String name,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.tagService.findTagsByName(name));
    }

    @PostMapping("/eventTags")
    public ResponseEntity<Object> addTagToEvent(@RequestBody Map<String, String> params,
            @AuthenticationPrincipal User user) {
        try {
            tagService.addTagToEvent(params, user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/eventTags/{eventTagId}")
    public ResponseEntity<Object> deleteEventTag(@PathVariable Long eventTagId, @AuthenticationPrincipal User user) {
        try {
            tagService.delete(eventTagId, user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (DataAccessException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
