package com.eventus.backend.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


import com.eventus.backend.models.Coordinates;
import com.eventus.backend.models.Event;
import com.eventus.backend.models.Hosting;
import com.eventus.backend.models.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HostingModelTests {

    @Mock
    Event event;
    @Mock
    Location location;

    Hosting hosting;
    Hosting hosting2;

    @Before
    public void setUp(){

        when(event.getTitle()).thenReturn("Event 1");
        when(event.getId()).thenReturn(1L);
        when(location.getId()).thenReturn(1L);
        when(location.getName()).thenReturn("Location 1");
        when(location.getPrice()).thenReturn(100.0);
        when(location.getCoordinates()).thenReturn(new Coordinates("22", "33"));

        hosting = new Hosting();
        hosting.setId(1L);
        hosting.setPrice(location.getPrice());
        hosting.setAccepted(null);
        hosting.setEvent(event);
        hosting.setLocation(location);

        hosting2 = new Hosting();

    }

    @Test
    public void setUpTest(){

        assertEquals(hosting.getId(), 1L);
        assertEquals(hosting.getEvent(), event);
        assertEquals(hosting.getEventId(), 1L);
        assertEquals(hosting.getLocation(), location);
        assertEquals(hosting.getLocationId(), 1L);
        assertEquals(hosting.getPrice(), 100.0);
        assertEquals(hosting.getClass(), Hosting.class);
    }

    @Test
    public void getLocationTest(){
        Location location = hosting.getLocation();
        Location location2 = hosting2.getLocation();

        Coordinates coordinates = location.getCoordinates();

        assertFalse(location == null);
        assertTrue(location2 == null);
        assertEquals(coordinates.getLongitude(), 33);
        assertEquals(coordinates.getLatitude(), 22);

        assertEquals(location.getName(), "Location 1");
        assertEquals(location.getPrice(), 100.0);
    }

    @Test
    public void getEventTest(){
        Event event = hosting.getEvent();
        Event event2 = hosting2.getEvent();

        assertFalse(event == null);
        assertTrue(event2 == null);

        assertEquals(event.getTitle(), "Event 1");
    }

    @Test
    public void eventEqualsMethodPositiveTest(){
        assertTrue(hosting.equals((Hosting)hosting));
        assertEquals(hosting.hashCode(), hosting.hashCode());
    }


    @Test
    public void eventEqualsMethodNegativeTest(){
        assertFalse(hosting.equals((Hosting)hosting2));
        assertFalse(hosting2.equals((Hosting)hosting));
        assertNotEquals(hosting.hashCode(), hosting2.hashCode());
    }

    @Test
    public void eventToStringMethodTest(){
        System.out.println(hosting.toString());
        assertTrue(hosting.toString().contains("price="+hosting.getPrice()));
        assertTrue(hosting2.toString().contains("price="+hosting2.getPrice()));
    }
    
}
