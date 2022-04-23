package com.eventus.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.eventus.backend.models.Media;
import com.eventus.backend.models.User;
import com.eventus.backend.services.MediaService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MediaController {
	
	private final MediaService mediaService;
	
	@Autowired
	public MediaController(MediaService mediaService) {
		this.mediaService = mediaService;
	}
	
	@PostMapping("/media")
	public ResponseEntity<Object> uploadImage(@RequestParam MultipartFile media, @AuthenticationPrincipal User user) throws Exception {
		try {
			if(this.mediaService.validate(media)){
				this.mediaService.save(media.getBytes(), media.getOriginalFilename(), user);
				return ResponseEntity.ok().build();
			}else{
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Map.of("error","Extension no permitida. Debe ser JPG, PNG, JPEG, JFIF o MP4."));
			}
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping(value = "/media/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<FileSystemResource> downloadImage(@PathVariable Long id) {
		try {
			FileSystemResource resource = this.mediaService.findById(id);
			return ResponseEntity.ok(resource);
		} catch(ResponseStatusException e) {
			return ResponseEntity.notFound().build();
		}
	}
	  
	@GetMapping("/media")
	public ResponseEntity<List<Media>> getMedias(@RequestParam(defaultValue = "0") Integer numPag,  @AuthenticationPrincipal User user) {
		if(user.isAdmin()) {
			List<Media> result = this.mediaService.findAll(PageRequest.of(numPag, 20000));
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
	@GetMapping("/users/media")
	public ResponseEntity<List<Long>> getMediaByUser(@RequestParam(defaultValue = "0") Integer numPag,  @AuthenticationPrincipal User user) {
		List<Long> result = this.mediaService.findByUser(PageRequest.of(numPag, 20000), user.getId());
		if(result.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/media/{id}")
	public ResponseEntity deleteMedia(@PathVariable Long id, @AuthenticationPrincipal User user){
		try{
			this.mediaService.delete(id, user);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(IllegalArgumentException e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body((Map.of("error",e.getMessage())));
		}
		
		
	}

}
