package com.eventus.backend.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Media;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.FileSystemRepository;
import com.eventus.backend.repositories.MediaRepository;

@Service
public class MediaService implements IMediaService {
	
    private final FileSystemRepository fileSystemRepository;
    private final MediaRepository mediaRepository;
    
    @Autowired
    public MediaService(FileSystemRepository fileSystemRepository, MediaRepository mediaRepository) {
    	this.fileSystemRepository = fileSystemRepository;
    	this.mediaRepository = mediaRepository;
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

}
