package com.eventus.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.eventus.backend.services.ImageService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ImageController {
	
	private final ImageService imageService;
	
	@Autowired
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@PostMapping("/images")
	public ResponseEntity<Long> uploadImage(@RequestParam MultipartFile multipartImage) throws Exception {
		try {
			Long imageId = this.imageService.uploadImage(multipartImage);
			return ResponseEntity.ok(imageId);
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> downloadImage(@PathVariable Long id) {
		try {
			Resource resource = this.imageService.downloadImage(id);
			return ResponseEntity.ok(resource);
		} catch(ResponseStatusException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
