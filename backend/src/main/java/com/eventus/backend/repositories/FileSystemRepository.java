package com.eventus.backend.repositories;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

@Repository
public class FileSystemRepository {
	
	
	String RESOURCES_DIR = System.getProperty("user.dir") + "/src/main/resources/media/";

	public String save(byte[] content, String imageName) throws Exception {
		
		long date = new Date().getTime();
		Path newFile = Paths.get(RESOURCES_DIR + date + "-" + imageName);
		Files.createDirectories(newFile.getParent());
		Files.write(newFile, content);
		return date + "-" + imageName;
	}
	
	public FileSystemResource findInFileSystem(String location) {
	    try {
			String path = ResourceUtils.getFile("classpath:media/" + location).getAbsolutePath();
	        return new FileSystemResource(Paths.get(path));
	    } catch (Exception e) {
	        throw new IllegalArgumentException();
	    }
	}

}
