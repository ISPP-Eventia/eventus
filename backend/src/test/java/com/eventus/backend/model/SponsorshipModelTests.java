package com.eventus.backend.model;


import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Sponsorship;
import com.eventus.backend.models.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SponsorshipModelTests {
    

    @Mock
    User owner;
    @Mock
    Event event;
    Sponsorship sponsorship;
    Sponsorship sponsorship2;
    
    @Before
    public void setUp(){


        when(event.getTitle()).thenReturn("Event 1");
        when(event.getPrice()).thenReturn(10.0);
        when(owner.getEmail()).thenReturn("pablo@gmail.com");
        when(owner.getLastName()).thenReturn("Gonzalez");

        sponsorship = new Sponsorship();
        sponsorship.setId(1L);
        sponsorship.setEvent(event);
        sponsorship.setUser(owner);
        sponsorship.setName("Sponsor 1");
        sponsorship.setQuantity(1000.0);
        sponsorship.setAccepted(false);

        sponsorship2 = new Sponsorship();
    }

    @Test
    public void setUpTest(){
        when(owner.getFirstName()).thenReturn("pabgonmon");

        assertEquals(sponsorship.getId(), 1L);
        assertEquals(sponsorship.getEvent(), event);
        assertEquals(sponsorship.getName(), "Sponsor 1");
        assertEquals(sponsorship.getQuantity(), 1000.0);
        assertEquals(sponsorship.getUser(), owner);
        assertEquals(sponsorship.getUser().getFirstName(), "pabgonmon");
        assertEquals(sponsorship.getUser().getEmail(), "pablo@gmail.com");
        assertEquals(sponsorship.getUser().getLastName(), "Gonzalez");
        assertEquals(sponsorship.getClass(), Sponsorship.class);
    }

    @Test
    public void getEventTest(){
        Event event = sponsorship.getEvent();
        Event event2 = sponsorship2.getEvent();

        assertFalse(event==null);
        assertTrue(event2==null);
        assertEquals(sponsorship.getEvent().getPrice(), 10.0);
        assertEquals(event.getTitle(), "Event 1");
    }


    @Test
    public void getOwnerTest(){
        User owner = sponsorship.getUser();
        User owner2 = sponsorship2.getUser();

        assertTrue(owner != null);
        assertTrue(owner2 == null);
        assertEquals(owner.getEmail(), "pablo@gmail.com");
        assertEquals(owner.getLastName(), "Gonzalez");
    }

    @Test
    public void sponsorshipEqualsMethodPositiveTest(){
        assertTrue(sponsorship.equals((Sponsorship)sponsorship));
        assertEquals(sponsorship.hashCode(), sponsorship.hashCode());
    }

    @Test
    public void sponsorshipEqualsMethodNegativeTest(){
        assertFalse(sponsorship.equals((Sponsorship)sponsorship2));
        assertFalse(sponsorship2.equals((Sponsorship)sponsorship));
        assertNotEquals(sponsorship.hashCode(), sponsorship2.hashCode());
    }

    @Test
    public void sponsorshipToStringMethodTest(){
        assertTrue(sponsorship.toString().contains("name='"+sponsorship.getName()+"'"));
        assertTrue(sponsorship2.toString().contains("name='"+sponsorship2.getName()+"'"));
    }
}
