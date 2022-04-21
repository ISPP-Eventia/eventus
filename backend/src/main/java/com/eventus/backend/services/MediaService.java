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
import com.eventus.backend.repositories.FileSystemRepository;
import com.eventus.backend.repositories.MediaRepository;
import com.eventus.backend.repositories.SponsorshipRepository;

@Service
public class MediaService implements IMediaService {
	
    private final FileSystemRepository fileSystemRepository;
    private final MediaRepository mediaRepository;
    private final SponsorshipRepository sponsorshipRepository;
    
    @Autowired
    public MediaService(FileSystemRepository fileSystemRepository, MediaRepository mediaRepository, SponsorshipRepository sponsorshipRepository) {
    	this.fileSystemRepository = fileSystemRepository;
    	this.mediaRepository = mediaRepository;
        this.sponsorshipRepository = sponsorshipRepository;
    }

    @Override
    public void save(byte[] bytes, String mediaName, User user) throws Exception {
        String location = fileSystemRepository.save(bytes, mediaName);
        Media media = new Media();
        media.setPath(location);
        media.setTitle(mediaName);
        media.setUploadDate(LocalDate.now());
        media.setOwner(user);

        this.mediaRepository.save(media);
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
		List<Long> media = this.mediaRepository.findByUserId(p,id);
		return media;
	}

    
    @Override
    public void parseEventMediaIds(List<Long> mediaIds, Event event) {
        //Validate that those media owner is the logged user
        Set<Media> media = new HashSet<>();
        mediaIds.stream().forEach(id ->{
            Media m = this.mediaRepository.findById(id).orElse(null);
            m.setEvent(event);
            media.add(m);
        });
        this.mediaRepository.saveAll(media);
    }

    @Override
    public void parseLocationMediaIds(List<Long> mediaIds, Location location) {
        //Validate that those media owner is the logged user
        Set<Media> media = new HashSet<>();
        mediaIds.stream().forEach(id ->{
            Media m = this.mediaRepository.findById(id).orElse(null);
            m.setLocation(location);
            media.add(m);
        });
        this.mediaRepository.saveAll(media);
    }

    @Override
    public void parseSponsorshipMediaIds(Long mediaId, Sponsorship sponsorship) {
        sponsorship.setMedia(this.mediaRepository.findById(mediaId).orElse(null));
        this.sponsorshipRepository.save(sponsorship);
    }

    @Override
    public boolean validate(MultipartFile media) {
        String[] arr = media.getOriginalFilename().trim().split("\\.");
        String extension = arr[arr.length-1];
        System.out.println(extension);
        System.out.println(extension == "png" || extension == "jpg" || extension == "jpeg" || extension ==  "mp4");
        return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg") || extension.equals("jfif") || extension.equals("mp4");
    }

}
