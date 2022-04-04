package com.eventus.backend.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Image;
import com.eventus.backend.models.Location;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserModelTests {
    @Mock
    Image imageDummy;
    @Mock
    Event eventDummy;
    @Mock
    Participation participationDummy;
    @Mock
    Location locationDummy;
    @Mock
    Sponsorship sponsorshipDummy;
    User user;
    static LocalDate date1 = LocalDate.of(2000, Month.FEBRUARY, 14);
    User user2;
    static LocalDate date2 = LocalDate.of(2001, Month.NOVEMBER, 14);

    @Before
    public void setUp(){
        Set<Participation> participations = new HashSet<>();
        participations.add(participationDummy);
        Set<Event> events = new HashSet<>();
        events.add(eventDummy);
        Set<Location> locations = new HashSet<>();
        locations.add(locationDummy);
        Set<Sponsorship> sponsorships = new HashSet<>();
        sponsorships.add(sponsorshipDummy);

        user = new User();

        user.setId(1l);
        user.setFirstName("Pepe");
        user.setLastName("Rodriguez");
        user.setBirthDate(date1);
        user.setEmail("peperod@email.example");
        user.setPassword("$up3r$€cr3tP4s$w0rd");
        user.setImage(imageDummy);
        
        user2 = new User();
        user2.setId(2l);
        user2.setFirstName("Manuel");
        user2.setLastName("García");
        user2.setBirthDate(date2);
        user2.setEmail("manugar@email.example");
        user2.setPassword("$up3r$€cr3tP4s$w0rd");
        user2.setImage(imageDummy);
        user2.setParticipations(participations);
        user2.setEvents(events);
        user2.setSponsors(sponsorships);
        user2.setLocations(locations);
        
    }

    @Test
    public void setUpTest(){
        when(imageDummy.getTitle()).thenReturn("Image of sunflowres");
        when(participationDummy.getUser()).thenReturn(user2);
        when(sponsorshipDummy.getUser()).thenReturn(user);
        when(eventDummy.getTitle()).thenReturn("Dungons & Dragons rol play");
        when(locationDummy.getDescription()).thenReturn("Against The Giants campaign of Dungons & Dragons");

        assertEquals(user2.getId(), 2l);
        assertEquals(user2.getFirstName(), "Manuel");
        assertEquals(user2.getLastName(), "García");
        assertEquals(user2.getBirthDate(), date2);
        assertEquals(user2.getEmail(), "manugar@email.example");
        assertEquals(user2.getPassword(), "$up3r$€cr3tP4s$w0rd");
        assertEquals(user2.getImage().getTitle(), "Image of sunflowres");
        assertEquals(user2.getParticipations().size(), 1);
        assertEquals(((Participation)user2.getParticipations().toArray()[0]).getUser(), user2);
        assertEquals(user2.getEvents().size(), 1);
        assertEquals(((Event)user2.getEvents().toArray()[0]).getTitle(), "Dungons & Dragons rol play");
        assertEquals(user2.getSponsors().size(), 1);
        assertEquals(((Sponsorship)user2.getSponsors().toArray()[0]).getUser(), user);
        assertEquals(user2.getLocations().size(), 1);
        assertEquals(((Location)user2.getLocations().toArray()[0]).getDescription(), "Against The Giants campaign of Dungons & Dragons");
    }

    @Test
    public void userGetUsernameMethodTest(){
        assertEquals(user.getUsername(), "peperod@email.example");
        assertEquals(user2.getUsername(), "manugar@email.example");
    }

    @Test
    public void userGetAuthoritiesMethodTest(){
        assertNotNull(user.getAuthorities());
        assertNotNull(user2.getAuthorities());
    }

    @Test
    public void userIsAccountNonExpiredMethodTest(){
        assertTrue(user.isAccountNonExpired());
        assertTrue(user2.isAccountNonExpired());
    }

    @Test
    public void userIsAccountNonLockedMethodTest(){
        assertTrue(user.isAccountNonLocked());
        assertTrue(user2.isAccountNonLocked());
    }

    @Test
    public void userIsCredentialsNonExpiredMethodTest(){
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user2.isCredentialsNonExpired());
    }
    

    @Test
    public void userIsEnabledMethodTest(){
        assertTrue(user.isEnabled());
        assertTrue(user2.isEnabled());
    }

    @Test
    public void userEqualsMethodPositiveTest(){
        assertTrue(user.equals(user));
        assertEquals(user.hashCode(), user.hashCode());
    }

    @Test
    public void userEqualsMethodNegativeTest(){
        assertFalse(user.equals((Object)user2));
        assertFalse(user2.equals((Object)user));
        assertNotEquals(user.hashCode(), user2.hashCode());
    }

    @Test
    public void userToStringMethodNegativeTest(){
        assertTrue(user.toString().contains("firstName='"+user.getFirstName()+"'"));
        assertTrue(user2.toString().contains("firstName='"+user2.getFirstName()+"'"));
    }
}
