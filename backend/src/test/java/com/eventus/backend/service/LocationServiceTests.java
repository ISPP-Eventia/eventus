package com.eventus.backend.service;

import com.eventus.backend.models.Coordinates;
import com.eventus.backend.models.Location;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.LocationRepository;
import com.eventus.backend.services.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LocationServiceTests {
    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationRepository locationRepository;
    private List<Location> locationList;

    private Location location;

    private Location location2;

    private Location location3;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User();
        this.user.setId(1L);
        this.user.setEmail("test@gmail.com");
        this.user.setPassword("test");
        this.locationList = new ArrayList<>();
        location = new Location();
        location2 = new Location();
        location3 = new Location();
        location.setId(1L);
        location.setName("Test");
        location.setDescription("Test");
        location.setOwner(user);
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(1.0);
        coordinates.setLongitude(1.0);
        location.setCoordinates(coordinates);
        this.locationList.add(location);
        location2.setId(2L);
        location2.setName("Test2");
        location2.setDescription("Test2");
        location2.setOwner(user);
        coordinates.setLatitude(1.0);
        coordinates.setLongitude(1.0);
        location2.setCoordinates(coordinates);
        this.locationList.add(location2);
        location3.setId(3L);
        location3.setName("Test3");
        location3.setDescription("Test3");
        location3.setOwner(user);
        coordinates.setLatitude(1.0);
        coordinates.setLongitude(1.0);
        location3.setCoordinates(coordinates);
        this.locationList.add(location3);
    }
    @Test
    void testGetLocations() {
        when(locationRepository.findAll(PageRequest.of(0, 10))).thenReturn(locationList);
        List<Location> locations = locationService.findAll(PageRequest.of(0, 10));
        assertNotNull(locations);
        assertEquals(3, locations.size());
        assertEquals(locationList, locations);
    }

    @Test
    void testGetUserLocations() {
        when(locationRepository.findByOwnerId(user.getId(),PageRequest.of(0,10))).thenReturn(locationList);
        List<Location> locations = locationService.findByOwnerId(user.getId(),PageRequest.of(0,10));
        assertNotNull(locations);
        assertEquals(3, locations.size());
        assertEquals(locationList, locations);
    }

    @Test
    void testGetLocationsById() {
        Location location = locationList.get(0);
        when(locationRepository.findById(location.getId())).thenReturn(Optional.ofNullable(location));
        Location res = locationService.findById(location.getId());
        assertNotNull(res);
        assertEquals(location, res);
    }

    @Test
    void testCreate() {
        when(locationRepository.save(location)).thenReturn(location);
        Location res = locationService.create(location,user);
        assertNotNull(res);
        assertEquals(user, res.getOwner());
        location.setOwner(user);
        assertEquals(location, res);
    }

    @Test
    void testDelete(){
        User admin=new User();
        admin.setId(2L);
        admin.setAdmin(true);
        assertThrows(IllegalArgumentException.class, () -> locationService.deleteById(location.getId(),admin));
        when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        locationService.deleteById(location.getId(),admin);
        locationService.deleteById(location.getId(),user);
        verify(locationRepository, times(2)).deleteById(location.getId());
    }

    @Test
    void testSave(){
        when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        when(locationRepository.save(location)).thenReturn(location);
        locationService.save(location);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testUpdate(){
        User admin=new User();
        admin.setId(2L);
        admin.setAdmin(true);
        when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        when(locationRepository.save(location)).thenReturn(location);
        locationService.update(location2,location.getId(),user);
        locationService.update(location2,location.getId(),admin);
        verify(locationRepository, times(2)).save(location);
    }

}
