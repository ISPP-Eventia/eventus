package com.eventus.backend.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.eventus.backend.models.Image;

public interface IImageService {
	
	public Long uploadImage(MultipartFile multipartImage) throws Exception;
	public Image findById(Long id);
	Resource downloadImage(Long id);

}
