package com.eventus.backend.repositories;

import java.util.Optional;

import com.eventus.backend.models.EventTag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagRepository extends CrudRepository<EventTag, Long> {

    Optional<EventTag> findByEventIdAndTagId(Long eventId, Long tagId);

}
