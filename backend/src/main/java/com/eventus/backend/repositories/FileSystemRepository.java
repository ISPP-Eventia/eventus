package com.eventus.backend.repositories;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Repository
public class FileSystemRepository {
	
	String RESOURCES_DIR = System.getProperty("user.dir") + "\\src\\main\\resources\\media\\";

	public String save(byte[] content, String imageName) throws Exception {
		System.out.println(RESOURCES_DIR);
		Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);
		Files.createDirectories(newFile.getParent());
		Files.write(newFile, content);
		return newFile.toAbsolutePath().toString();
	}
	
	public FileSystemResource findInFileSystem(String location) {
	    try {
	        return new FileSystemResource(Paths.get(location));
	    } catch (Exception e) {
	        throw new RuntimeException();
	    }
	}

}
