package com.eventus.backend.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eventus.backend.models.Coordinates;
import com.eventus.backend.models.Event;
import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.Image;
import com.eventus.backend.models.Location;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EventModelTests {
    @Mock
    User user;
    Event event1;
    Event event2;
    static LocalDateTime date1 = LocalDateTime.now();
    List<Image> images;
    Set<Sponsorship> sponsors;
    Set<Participation> participations;
    Set<Hosting> hostings;

    @Before
    public void setUp(){
        images = new ArrayList<>();
        Image image = mock(Image.class);
        images.add(image);

        sponsors = new HashSet<>();
        Sponsorship sponsor1 = mock(Sponsorship.class);        
        Sponsorship sponsor2 = mock(Sponsorship.class);   
        sponsors.add(sponsor1);
        sponsors.add(sponsor2);

        participations = new HashSet<>();
        Participation participation1 = mock(Participation.class);  
        Participation participation2 = mock(Participation.class);  
        Participation participation3 = mock(Participation.class); 
        participations.add(participation1); 
        participations.add(participation2); 
        participations.add(participation3); 

        hostings = new HashSet<>();
        Hosting hosting1 = mock(Hosting.class);
        Location location1 = mock(Location.class);
        
        Mockito.when(hosting1.isAccepted()).thenReturn(true);
        Mockito.when(hosting1.getLocation()).thenReturn(location1);
        Mockito.when(location1.getCoordinates()).thenReturn(new Coordinates("5.3", "-3.1"));
        hostings.add(hosting1);
        
        event1 = new Event();
		event1.setId(1L);
		event1.setTitle("Event 1");
		event1.setDescription("Event 1 for testing");
        event1.setStartDate(date1);
        event1.setEndDate(date1.plusHours(4L));
		event1.setPrice(2.00);
		event1.setOrganizer(user);
		event1.setImages(images);
        event1.setSponsors(sponsors);
        event1.setHostings(hostings);
        event1.setParticipations(participations);
		
		event2 = new Event(2L, "Event 2", 5., "Event 2 for testing");
    }

    @Test
    public void setUpTest(){
        when(user.getFirstName()).thenReturn("carvilgar1");

        assertEquals(event1.getId(), 1L);
        assertEquals(event1.getTitle(), "Event 1");
        assertEquals(event1.getDescription(), "Event 1 for testing");
        assertEquals(event1.getStartDate(), date1);
        assertEquals(event1.getEndDate(), date1.plusHours(4L));
        assertEquals(event1.getPrice(), 2.00);
        assertEquals(event1.getOrganizer(), user);
        assertEquals(event1.getOrganizer().getFirstName(), "carvilgar1");
        assertEquals(event1.getImages().size(), 1);
        assertEquals(event1.getParticipations().size(), 3);
        assertEquals(event1.getHostings().size(), 1);
        assertEquals(event1.getSponsors().size(), 2);
    }

    @Test
    public void getEventCoordinatesMethodTest(){
        Coordinates coordinates = event1.getEventCoordinates();
        Coordinates coordinates2 = event2.getEventCoordinates();

        assertFalse(coordinates == null);
        assertTrue(coordinates2 == null);
        assertEquals(coordinates.getLongitude(), -3.1);
        assertEquals(coordinates.getLatitude(), 5.3);
    }

    @Test
    public void eventEqualsMethodPositiveTest(){
        assertTrue(event1.equals((Event)event1));
        assertEquals(event1.hashCode(), event1.hashCode());
    }

    @Test
    public void eventEqualsMethodNegativeTest(){
        assertFalse(event1.equals((Event)event2));
        assertFalse(event2.equals((Event)event1));
        assertNotEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void eventToStringMethodTest(){
        assertTrue(event1.toString().contains("title='"+event1.getTitle()+"'"));
        assertTrue(event2.toString().contains("title='"+event2.getTitle()+"'"));
    }
    
}
