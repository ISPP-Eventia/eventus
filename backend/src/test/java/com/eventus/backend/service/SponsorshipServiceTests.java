package com.eventus.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;
import com.eventus.backend.repositories.SponsorshipRepository;
import com.eventus.backend.services.EventService;
import com.eventus.backend.services.SponsorshipService;
import com.eventus.backend.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class SponsorshipServiceTests {
    @Autowired
    private SponsorshipService sponsorService;

    @Mock
    private SponsorshipRepository sponsorRepository;
    @Mock
    private EventService eventService;
    @Mock
    private StripeService stripeService;

    @Mock
    Sponsorship sponsorship;
    @Mock
    Event event;
    private User user;


    @BeforeEach
    void setUp() {
        sponsorService = new SponsorshipService(sponsorRepository, eventService, stripeService);
        this.user = new User();
        this.user.setId(1L);
        this.user.setEmail("test@gmail.com");
        this.user.setPassword("test");
        
        when(sponsorship.getId()).thenReturn(1L);
        when(sponsorship.getUser()).thenReturn(user);
        when(sponsorship.getEvent()).thenReturn(event);
        when(event.getId()).thenReturn(1L);
        when(event.getOrganizer()).thenReturn(user);
    }
    @Test
    void testfindSponsorById() {
        when(sponsorRepository.findById(sponsorship.getId())).thenReturn(Optional.of(sponsorship));
        
        Sponsorship findSponsorById = sponsorService.findSponsorById(sponsorship.getId());
        assertNotNull(findSponsorById);
        assertEquals(this.sponsorship, findSponsorById);
    }

    @Test
    void testfindfindAllSponsor() {
        List<Sponsorship> sponsorshipsResponse = new ArrayList<>();
        sponsorshipsResponse.add(this.sponsorship);

        when(sponsorRepository.findAll(PageRequest.of(0, 10))).thenReturn(sponsorshipsResponse);
        
        List<Sponsorship> sponsorship = sponsorService.findAll(PageRequest.of(0, 10));
        assertNotNull(sponsorship);
        assertEquals(sponsorshipsResponse, sponsorship);
        assertEquals(1, sponsorship.size());
    }

    @Test
    void testfindSponsorByUserId() {
        List<Sponsorship> sponsorshipsResponse = new ArrayList<>();
        sponsorshipsResponse.add(this.sponsorship);

        when(sponsorRepository.findSponsorByUserId(user.getId(), PageRequest.of(0, 10))).thenReturn(sponsorshipsResponse);
        
        List<Sponsorship> sponsorship = sponsorService.findSponsorByUserId(user.getId(), PageRequest.of(0, 10));
        assertNotNull(sponsorship);
        assertEquals(sponsorshipsResponse, sponsorship);
    }

    @Test
    void testfindSponsorByEventId() {
        List<Sponsorship> sponsorshipsResponse = new ArrayList<>();
        sponsorshipsResponse.add(this.sponsorship);

        when(eventService.findById(event.getId())).thenReturn(event);
        when(sponsorRepository.findSponsorByEventId(event.getId(), PageRequest.of(0, 10))).thenReturn(sponsorshipsResponse);
        
        List<Sponsorship> sponsorship = sponsorService.findSponsorByEventId(event.getId(), PageRequest.of(0, 10), user);
        assertNotNull(sponsorship);
        assertEquals(sponsorshipsResponse, sponsorship);
        assertEquals(1, sponsorship.size());
    }

   @Test
    void testSave() {
        when(sponsorRepository.save(sponsorship)).thenReturn(sponsorship);
        
        sponsorService.save(sponsorship);
        
        verify(sponsorRepository, times(1)).save(sponsorship);
    }

    @Test
    void testDelete(){
        
        when(sponsorRepository.findById(sponsorship.getId())).thenReturn(Optional.of(sponsorship));
        
        sponsorService.delete(sponsorship);
        
        verify(sponsorRepository, times(1)).delete(sponsorship);
    }

    @Test
    void testDeleteById(){
        
        when(sponsorRepository.findById(sponsorship.getId())).thenReturn(Optional.of(sponsorship));
        
        sponsorService.deleteById(sponsorship.getId());
        
        verify(sponsorRepository, times(1)).deleteById(sponsorship.getId());
    }

    @Test
    void testCreate(){
        Sponsorship expectedSponsorship  = new Sponsorship();
        expectedSponsorship.setEvent(event);
        expectedSponsorship.setQuantity(1.);
        expectedSponsorship.setName("Create test");

        Map<String, String> map = new HashMap<>();
        map.put("eventId", "1");
        map.put("quantity", "1");
        map.put("name", "Create test");

        when(eventService.findById(event.getId())).thenReturn(event);
        when(sponsorRepository.save(any(Sponsorship.class))).thenReturn(expectedSponsorship);

        Sponsorship sponsor = sponsorService.create(map, user);
        
        assertEquals(expectedSponsorship, sponsor);
        verify(sponsorRepository, times(1)).save(expectedSponsorship);
    }

    @Test
    void testfindByEventAndState(){
        List<Sponsorship> sponsorshipsResponse = new ArrayList<>();
        sponsorshipsResponse.add(this.sponsorship);

        when(sponsorRepository.findByEventAndState(sponsorship.getEvent().getId(), true, PageRequest.of(0,10))).thenReturn(sponsorshipsResponse);
        List<Sponsorship> sponsorships = sponsorService.findByEventAndState(this.sponsorship.getEvent().getId(), "accepted",PageRequest.of(0,10));
        
        assertNotNull(sponsorships);
        assertEquals(1, sponsorships.size());
    }

    @Test
    void testresolveHosting() throws StripeException {
        when(sponsorRepository.findById(sponsorship.getId())).thenReturn(Optional.of(sponsorship));
        when(stripeService.createSponsorshipPayment(sponsorship)).thenReturn(new PaymentIntent());

        sponsorService.resolveSponsorship(false,sponsorship.getId(), user);

        verify(sponsorRepository, times(1)).save(sponsorship);
    }

}
