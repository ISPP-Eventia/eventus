package com.eventus.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eventus.backend.models.Coordinates;
import com.eventus.backend.models.Event;
import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.Location;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.HostingRepository;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.HostingService;
import com.eventus.backend.services.LocationService;
import com.eventus.backend.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class HostingServiceTests {
    @Autowired
    private HostingService hostingService;
    @Mock
    private HostingRepository hostingRepository;
    @Mock
    private LocationService locationService;
    @Mock
    private EventService eventService;
    @Mock
    private StripeService stripeService;
    @Mock
    User user;
    @Mock
    Hosting hosting1;
    @Mock
    Hosting hosting2;
    List<Hosting> hostings;

    @BeforeEach
    void setUp() {
        hostingService = new HostingService(hostingRepository, locationService, eventService, stripeService);
        LocalDateTime date1 = LocalDateTime.now();
         

        this.hostings = new ArrayList<>();
        hostings.add(hosting1);
        hostings.add(hosting2);

        Event event1 = new Event();
		event1.setId(1L);
		event1.setTitle("Event 1");
		event1.setDescription("Event 1 for testing");
        event1.setStartDate(date1);
        event1.setEndDate(date1.plusHours(4L));
		event1.setPrice(2.00);
		event1.setOrganizer(user);
        event1.setHostings(new HashSet<>(hostings));

        Location location = new Location();
		location.setId(1L);
		location.setName("Pista de futbol");
		location.setDescription("Descripcion");
		location.setPrice(100.0);
		location.setOwner(user);
		location.setCoordinates(mock(Coordinates.class));

        when(user.getId()).thenReturn(1L);
        when(hosting1.getEvent()).thenReturn(event1);
        when(hosting1.getLocation()).thenReturn(location);
        when(hosting1.getId()).thenReturn(1L);
    }

    @Test
    void testfindAll() {
        when(hostingRepository.findAll(PageRequest.of(0, 10))).thenReturn(hostings);
        
        List<Hosting> hostings = hostingService.findAll(PageRequest.of(0, 10));
        assertNotNull(hostings);
        assertEquals(2, hostings.size());
        assertEquals(this.hostings, hostings);
    }

    @Test
    void testFindByEventId() {
        Event event1 = new Event();
		event1.setId(1L);
		event1.setTitle("Event 1");
		event1.setDescription("Event 1 for testing");
		event1.setPrice(2.00);
		event1.setOrganizer(user);
        event1.setHostings(new HashSet<>(hostings));

        when(eventService.findById(hosting1.getEvent().getId())).thenReturn(event1);
        when(hostingRepository.findByEventId(hosting1.getEvent().getId(),PageRequest.of(0,10))).thenReturn(hostings);
        List<Hosting> hosting = hostingService.findByEventId(hosting1.getEvent().getId(),PageRequest.of(0,10), user);
        assertNotNull(hosting);
        assertEquals(2, hosting.size());
        assertEquals(this.hostings, hosting);
    }

    @Test
    void testGetLocationsById() {
        when(hostingRepository.findById(hosting1.getId())).thenReturn(Optional.of(hosting1));
        
        Hosting hosting = hostingService.findById(hosting1.getId());
        assertNotNull(hosting);
        assertEquals(hosting1, hosting);
    }

    @Test
    void testfindHostingByEventIdAndLocationId() {
        when(hostingRepository.findByEventAndLocation(hosting1.getEvent().getId(), hosting1.getLocation().getId())).thenReturn(Optional.of(hosting1));
        Hosting hosting = hostingService.findHostingByEventIdAndLocationId(hosting1.getEvent().getId(), hosting1.getLocation().getId());
        assertNotNull(hosting);
        assertEquals(this.hosting1, hosting);
    }

    @Test
    void testresolveHosting() throws StripeException {
        when(hostingRepository.findById(hosting1.getId())).thenReturn(Optional.of(hosting1));
        when(stripeService.createHostingPayment(hosting1)).thenReturn(new PaymentIntent());
        
        hostingService.resolveHosting(false,hosting1.getLocation().getId(), user);
        
        verify(hostingRepository, times(1)).save(hosting1);
    }

    @Test
    void testfindByLocationId(){
        List<Hosting> hostings = new ArrayList<>();
        hostings.add(hosting1);

        Location location = new Location();
		location.setId(1L);
		location.setName("Pista de futbol");
		location.setDescription("Descripcion");
		location.setPrice(100.0);
		location.setOwner(user);
		location.setCoordinates(mock(Coordinates.class));

        when(hostingRepository.findByLocationId(hosting1.getLocation().getId(), PageRequest.of(0,10))).thenReturn(hostings);
        when(locationService.findById(hosting1.getLocation().getId())).thenReturn(location);
        List<Hosting> hosting = hostingService.findByLocationId(hosting1.getLocation().getId(), PageRequest.of(0,10), user);
        assertNotNull(hosting);
        assertEquals(hostings, hosting);
    }

    @Test
    void testfindByEventAndState(){
        List<Hosting> hostings = new ArrayList<>();
        hostings.add(hosting1);
        when(hostingRepository.findByEventAndState(hosting1.getEvent().getId(), true, PageRequest.of(0,10))).thenReturn(hostings);
        List<Hosting> hosting = hostingService.findByEventAndState(hosting1.getEvent().getId(), "accepted",PageRequest.of(0,10));
        assertNotNull(hosting);
        assertEquals(hostings, hosting);
    }

    @Test
    void testCreate(){
        User user2 = new User();

        user2.setId(1l);
        user2.setFirstName("Pepe");
        user2.setLastName("Rodriguez");
        user2.setEmail("peperod@email.example");
        user2.setPassword("$up3r$€cr3tP4s$w0rd");

        Event event1 = new Event();
		event1.setId(1L);
		event1.setTitle("Event 1");
		event1.setDescription("Event 1 for testing");
		event1.setPrice(2.00);
		event1.setOrganizer(user2);
        event1.setHostings(new HashSet<>(hostings));

        Location location = new Location();
		location.setId(1L);
		location.setName("Pista de futbol");
		location.setDescription("Descripcion");
		location.setPrice(100.0);
		location.setOwner(user2);
		location.setCoordinates(mock(Coordinates.class));

        Hosting hosting = new Hosting();
        hosting.setId(1L);
        hosting.setPrice(location.getPrice());
        hosting.setAccepted(null);
        hosting.setEvent(event1);
        hosting.setLocation(location);
        
        Map<String, String> map = new HashMap<>();
        map.put("eventId", "1");
        map.put("locationId", "1");
        map.put("price", "100.0");
        
        when(hostingRepository.findByEventAndLocation(anyLong(),anyLong())).thenReturn(Optional.empty());
        when(eventService.findById(anyLong())).thenReturn(event1);
        when(locationService.findById(anyLong())).thenReturn(location);
        
        hostingService.create(map);

        verify(hostingRepository, times(1)).findByEventAndLocation(1l, 1l);
    }

    @Test
    void testSave(){
        when(hostingRepository.findById(hosting1.getId())).thenReturn(Optional.of(hosting1));
        when(hostingRepository.save(hosting1)).thenReturn(hosting1);
        hostingService.save(hosting1);
        verify(hostingRepository, times(1)).save(hosting1);
    }

    @Test
    void testDelete(){
        when(hostingRepository.findById(hosting1.getId())).thenReturn(Optional.of(hosting1));
        
        hostingService.delete(hosting1);

        verify(hostingRepository, times(1)).delete(hosting1);
    }

    @Test
    void testDeleteByID(){
        when(hostingRepository.findById(hosting1.getId())).thenReturn(Optional.of(hosting1));
        
        hostingService.deleteById(hosting1.getId());

        verify(hostingRepository, times(1)).deleteById(hosting1.getId());
    }

}
