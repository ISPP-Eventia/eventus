package com.eventus.backend.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.eventus.backend.models.Image;
import com.eventus.backend.repositories.ImageRepository;

@Service
public class ImageService implements IImageService {
	
	private final ImageRepository imageRepository;
	
	@Autowired
	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	@Override
	public Long uploadImage(MultipartFile multipartImage) throws Exception {
		Image imagedb = new Image();
		imagedb.setTitle(multipartImage.getOriginalFilename());
		imagedb.setContent(multipartImage.getBytes());
		imagedb.setUploadDate(LocalDate.now());
		
		return this.imageRepository.save(imagedb).getId();
	}

	@Override
	public Image findById(Long id) {		
		return this.imageRepository.findById(id).orElse(null);
	}

	@Override
	public Resource downloadImage(Long id) {
		Image image = this.findById(id);
		if(image!=null) {
			byte[] bytes = this.findById(id).getContent();
			return new ByteArrayResource(bytes);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
