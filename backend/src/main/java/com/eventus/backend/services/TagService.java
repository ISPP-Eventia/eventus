package com.eventus.backend.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.EventTag;
import com.eventus.backend.models.Tag;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.EventRepository;
import com.eventus.backend.repositories.EventTagRepository;
import com.eventus.backend.repositories.TagRepository;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    private final EventTagRepository eventTagRepository;

    private final EventRepository eventRepository;

    @Autowired
    public TagService(TagRepository tagRepository, EventTagRepository eventTagRepository,
            EventRepository eventRepository) {
        this.tagRepository = tagRepository;
        this.eventTagRepository = eventTagRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Tag> findByEventId(Long id, Pageable pageable) {
        return tagRepository.findByEventId(id, pageable);
    }

    @Override
    public Tag save(Tag tag) {
        String name = tag.getName();
        Optional<Tag> optTag = tagRepository.findByName(name);

        if (optTag.isPresent()) {
            throw new IllegalArgumentException("La etiqueta ya existe");
        }

        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAll(Pageable p) {
        return tagRepository.findAll(p);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public EventTag addTagToEvent(Map<String, String> params, User user) throws IllegalArgumentException {
        String eventId = params.get("eventId");
        String tagId = params.get("tagId");
        Validate.isTrue(StringUtils.isNotBlank(eventId) && StringUtils.isNumeric(eventId),
                "El eventId debe ser un número");
        Validate.isTrue(StringUtils.isNotBlank(tagId) && StringUtils.isNumeric(tagId), "El tagId debe ser un número");

        Event event = eventRepository.findById(Long.valueOf(eventId)).orElse(null);

        if (event == null) {
            throw new IllegalArgumentException("Evento no encontrado");
        }

        Tag tag = tagRepository.findById(Long.valueOf(tagId)).orElse(null);

        if (tag == null) {
            throw new IllegalArgumentException("Etiqueta no encontrada");
        }

        if (!event.getOrganizer().getId().equals(user.getId())) {
            throw new IllegalArgumentException("No eres el organizador de este evento");
        }

        Optional<EventTag> optEventTag = eventTagRepository.findByEventIdAndTagId(event.getId(), tag.getId());

        if (optEventTag.isPresent()) {
            throw new IllegalArgumentException("La etiqueta ya está asociada al evento");
        }

        EventTag eventTag = new EventTag();

        eventTag.setEvent(event);
        eventTag.setTag(tag);

        return eventTagRepository.save(eventTag);
    }

    @Override
    public void delete(Long eventTagId, User user) {
        EventTag eventTag = eventTagRepository.findById(eventTagId).orElse(null);
        if (eventTag == null) {
            throw new IllegalArgumentException("No existe dicha etiqueta para un evento");
        }

        if (!eventTag.getEvent().getOrganizer().getId().equals(user.getId())) {
            throw new IllegalArgumentException("No eres el organizador de este evento");
        }

        eventTagRepository.delete(eventTag);
    }

    @Override
    public List<Tag> findTagsByName(String name) {
        return tagRepository.findTop10ByNameStartingWith(name);
    }
}
