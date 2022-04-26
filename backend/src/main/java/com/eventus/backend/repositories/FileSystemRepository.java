package com.eventus.backend.repositories;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Repository
public class FileSystemRepository {
	
	
	String RESOURCES_DIR = "./src/main/resources/media/";

	public String save(byte[] content, String imageName) throws Exception {
		
		long date = new Date().getTime();
		Path newFile = Paths.get(RESOURCES_DIR + date + "-" + imageName);
		Files.createDirectories(newFile.getParent());
		Files.write(newFile, content);
		return date + "-" + imageName;
	}
	
	public FileSystemResource findInFileSystem(String location) {
	    try {
	        return new FileSystemResource(RESOURCES_DIR + location);
	    } catch (Exception e) {
	        throw new IllegalArgumentException();
	    }
	}

}
