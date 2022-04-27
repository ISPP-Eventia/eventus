package com.eventus.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.eventus.backend.models.Media;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.EventRepository;
import com.eventus.backend.repositories.FileSystemRepository;
import com.eventus.backend.repositories.LocationRepository;
import com.eventus.backend.repositories.MediaRepository;
import com.eventus.backend.repositories.SponsorshipRepository;
import com.eventus.backend.services.MediaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MediaServiceTests {
    @Autowired
    private MediaService mediaService;
    @Mock
    private FileSystemRepository fileSystemRepository;
    @Mock
    private MediaRepository mediaRepository;
    @Mock
    private SponsorshipRepository sponsorshipRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    User user;
    @Mock
    Media media;

    @BeforeEach
    void setUp() {
        mediaService = new MediaService(fileSystemRepository, mediaRepository, sponsorshipRepository, locationRepository, eventRepository);

        when(user.getId()).thenReturn(1L);
        when(media.getId()).thenReturn(1L);
        when(media.getOwner()).thenReturn(user);
        when(media.getPath()).thenReturn("path");
    }

    @Test
    void testFindByMediaId() {
        FileSystemResource md = new FileSystemResource("file");
        when(mediaRepository.findById(this.media.getId())).thenReturn(Optional.of(this.media));
        when(fileSystemRepository.findInFileSystem(this.media.getPath())).thenReturn(md);
        
        FileSystemResource media = mediaService.findById(this.media.getId());
        
        assertNotNull(media);
        assertEquals(md, media);
    }

    @Test
    void testFindAll() {
        List<Media> mediaList = new ArrayList<>();
        mediaList.add(this.media);
        
        when(mediaRepository.findAll(PageRequest.of(0, 10))).thenReturn(mediaList);
        
        List<Media> media2 = mediaService.findAll(PageRequest.of(0, 10));
        
        assertNotNull(media2);
        assertEquals(mediaList, media2);
    }

    @Test
    void testFindByUserId() {
        List<Long> mediaList = new ArrayList<>();
        mediaList.add(this.media.getId());

        when(mediaRepository.findByUserId(PageRequest.of(0, 10), user.getId())).thenReturn(mediaList);
        
        List<Long> media = mediaService.findByUser(PageRequest.of(0, 10), user.getId());
        
        assertNotNull(media);
        assertEquals(mediaList, media);
    }

    @Test
    void testValidate(){
        MultipartFile media = mock(MultipartFile.class);
        when(media.getOriginalFilename()).thenReturn("example.png");
        
        assertDoesNotThrow(()->{
            mediaService.validate(media);
        });
    }

    @Test
    void testValidateNegative(){
        MultipartFile media = mock(MultipartFile.class);
        when(media.getOriginalFilename()).thenReturn("example.blahblah");
        
        assertThrows(IllegalArgumentException.class, ()->{
            mediaService.validate(media);
        });
    }

    @Test
    void testSave() throws Exception{
        when(fileSystemRepository.save(new byte[50],"example.png")).thenReturn("path");
        when(mediaRepository.save(this.media)).thenReturn(this.media);
        
        mediaService.save(new byte[50],"example.png", user);
        
        verify(mediaRepository, times(1)).save(any(Media.class));
    }

    @Test
    void testDelete() throws Exception{
        when(mediaRepository.findById(this.media.getId())).thenReturn(Optional.of(this.media));
        
        mediaService.delete(this.media.getId(), user);
        
        verify(mediaRepository, times(1)).deleteById(this.media.getId());
    }

}
