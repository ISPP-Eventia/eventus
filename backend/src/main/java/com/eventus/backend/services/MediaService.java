package com.eventus.backend.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Location;
import com.eventus.backend.models.Media;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.EventRepository;
import com.eventus.backend.repositories.FileSystemRepository;
import com.eventus.backend.repositories.LocationRepository;
import com.eventus.backend.repositories.MediaRepository;
import com.eventus.backend.repositories.SponsorshipRepository;
import com.google.common.collect.Sets;

@Service
public class MediaService implements IMediaService {

    private final FileSystemRepository fileSystemRepository;
    private final MediaRepository mediaRepository;
    private final SponsorshipRepository sponsorshipRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public MediaService(FileSystemRepository fileSystemRepository, MediaRepository mediaRepository,
            SponsorshipRepository sponsorshipRepository, LocationRepository locationRepository,
            EventRepository eventRepository) {
        this.fileSystemRepository = fileSystemRepository;
        this.mediaRepository = mediaRepository;
        this.sponsorshipRepository = sponsorshipRepository;
        this.locationRepository = locationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Media save(byte[] bytes, String mediaName, User user) throws Exception {
        String location = fileSystemRepository.save(bytes, mediaName);
        Media media = new Media();
        media.setPath(location);
        media.setTitle(mediaName);
        media.setUploadDate(LocalDate.now());
        media.setOwner(user);

        return this.mediaRepository.save(media);
    }

    @Override
    public FileSystemResource findById(Long mediaId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return this.fileSystemRepository.findInFileSystem(media.getPath());
    }

    @Override
    public List<Media> findAll(Pageable p) {
        return this.mediaRepository.findAll(p);
    }

    @Override
    public List<Long> findByUser(Pageable p, Long id) {
        List<Long> media = this.mediaRepository.findByUserId(p, id);
        return media;
    }

    @Override
    public void parseEventMediaIds(List<Long> mediaIds, Event event, User user) {
        Event nEvent = this.eventRepository.findById(event.getId()).orElse(null);

        if (!nEvent.getOrganizer().getId().equals(user.getId())) {
            throw new IllegalArgumentException("No eres propietario de la infraestructura");
        }

        Set<Media> oldMedias = nEvent.getMedia();
        Set<Media> medias = Sets.newHashSet(this.mediaRepository.findAllById(mediaIds));

        oldMedias.removeAll(medias);
        oldMedias.forEach(m -> m.setEvent(null));

        this.mediaRepository.deleteAll(oldMedias);

        medias.forEach(media -> {
            if (!media.getOwner().getId().equals(user.getId())) {
                throw new IllegalArgumentException("No eres propietario de la media");

            }
            media.setEvent(nEvent);
        });

        this.mediaRepository.saveAll(medias);

    }

    @Override
    public void parseLocationMediaIds(List<Long> mediaIds, Location location, User user) {
        Location nlocation = this.locationRepository.findById(location.getId()).orElse(null);

        if (!nlocation.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException("No eres propietario de la infraestructura");
        }

        Set<Media> oldMedias = nlocation.getMedia();
        Set<Media> medias = Sets.newHashSet(this.mediaRepository.findAllById(mediaIds));

        oldMedias.removeAll(medias);
        oldMedias.forEach(m -> m.setLocation(null));

        this.mediaRepository.deleteAll(oldMedias);

        medias.forEach(media -> {
            if (!media.getOwner().getId().equals(user.getId())) {
                throw new IllegalArgumentException("No eres propietario de la media");

            }
            media.setLocation(nlocation);
        });

        this.mediaRepository.saveAll(medias);

    }

    @Override
    public void parseSponsorshipMediaIds(List<Long> mediaIds, Sponsorship sponsorship, User user) {
        Sponsorship nSponsorship = this.sponsorshipRepository.findById(sponsorship.getId()).orElse(null);

        if (!sponsorship.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("No eres propietario de la infraestructura");
        }

        Set<Media> oldMedias = nSponsorship.getMedia();
        Set<Media> medias = Sets.newHashSet(this.mediaRepository.findAllById(mediaIds));

        oldMedias.removeAll(medias);
        oldMedias.forEach(m -> m.setSponsorship(null));

        this.mediaRepository.deleteAll(oldMedias);

        medias.forEach(media -> {
            if (!media.getOwner().getId().equals(user.getId())) {
                throw new IllegalArgumentException("No eres propietario de la media");

            }
            media.setSponsorship(nSponsorship);
        });

        this.mediaRepository.saveAll(medias);
    }

    @Override
    public void validate(MultipartFile media) throws IllegalArgumentException {
        String[] arr = media.getOriginalFilename().trim().split("\\.");
        String extension = arr[arr.length - 1];
        if (!(extension.equals("png") || extension.equals("jpg") ||
                extension.equals("jpeg") || extension.equals("jfif") ||
                extension.equals("mp4"))) {
            throw new IllegalArgumentException("Extension no permitida. Debe ser PNG, JPG, JPEG, JFIF o MP4.");
        }
    }

    @Override
    public void delete(Long id, User user) {
        Media media = this.mediaRepository.findById(id).orElse(null);
        if (user.isAdmin() || media.getOwner().equals(user)) {
            this.mediaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("No eres el propietario de este archivo.");
        }
    }

}
