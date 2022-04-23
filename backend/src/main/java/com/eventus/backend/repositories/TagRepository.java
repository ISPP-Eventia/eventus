package com.eventus.backend.repositories;

import java.util.List;
import java.util.Optional;

import com.eventus.backend.models.Tag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    List<Tag> findAll(Pageable page);

    @Query("SELECT tag FROM Tag tag JOIN tag.eventTags et WHERE et.event.id = :eventId")
    List<Tag> findByEventId(@Param("eventId") Long eventId, Pageable page);

    Optional<Tag> findByName(String name);

    List<Tag> findTop10ByNameStartingWith(String name);
}
