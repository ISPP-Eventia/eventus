package com.eventus.backend.services;

import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;

import com.eventus.backend.models.Media;
import com.eventus.backend.models.User;

public interface IMediaService {
	
	 public void save(byte[] bytes, String mediaName, User user) throws Exception;
	 public List<Media> findAll(Pageable p);
	 public FileSystemResource findById(Long mediaId);
	 public List<Long> findByUser(Pageable p, Long id);

}
