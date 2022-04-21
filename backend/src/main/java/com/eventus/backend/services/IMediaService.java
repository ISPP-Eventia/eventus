package com.eventus.backend.services;

import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Location;
import com.eventus.backend.models.Media;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;

public interface IMediaService {
	
	 public void save(byte[] bytes, String mediaName, User user) throws Exception;
	 public List<Media> findAll(Pageable p);
	 public FileSystemResource findById(Long mediaId);
	 public List<Long> findByUser(Pageable p, Long id);
	 public void parseEventMediaIds(List<Long> mediaIds, Event event);
	 public void parseLocationMediaIds(List<Long> mediaIds, Location location);
	 public void parseSponsorshipMediaIds(Long mediaId, Sponsorship sponsorship);
	 public boolean validate(MultipartFile media);
}
