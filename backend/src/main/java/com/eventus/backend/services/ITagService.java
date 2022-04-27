package com.eventus.backend.services;

import java.util.List;
import java.util.Optional;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.EventTag;
import com.eventus.backend.models.Tag;
import com.eventus.backend.models.User;

import org.springframework.data.domain.Pageable;

public interface ITagService {

    List<Tag> findByEventId(Long id, Pageable pageable);

    Tag save(Tag tag);

    List<Tag> findAll(Pageable pageable);

    Optional<Tag> findByName(String name);

    EventTag addTagToEvent(Long eventId, Long tagId, User user) throws IllegalArgumentException;

    void addTagsToEvent(Event event, User user) throws IllegalArgumentException;

    void delete(Long eventTagId, User user) throws IllegalArgumentException;

    List<Tag> findTagsByName(String name);
}
