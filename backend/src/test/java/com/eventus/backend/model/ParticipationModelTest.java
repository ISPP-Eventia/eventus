package com.eventus.backend.model;

import com.eventus.backend.models.Event;
import com.eventus.backend.models.Participation;
import com.eventus.backend.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ParticipationModelTest {


        @Mock
        User user;
        @Mock
        Event event;

        Participation participation;
        static LocalDate date1 = LocalDate.of(2000, Month.FEBRUARY, 14);
        Participation participation2;

        @Before
        public void setUp(){


            when(event.getTitle()).thenReturn("Event 1");
            when(event.getPrice()).thenReturn(10.0);
            when(user.getEmail()).thenReturn("pedro@gmail.com");
            when(user.getLastName()).thenReturn("Pontiga");

            participation = new Participation();
            participation.setId(1L);
            participation.setEvent(event);
            participation.setUser(user);
            participation.setPrice(10.0);
            participation.setTicket("Ticket 1");
            participation.setBuyDate(date1);
            participation2 = new Participation();
        }

        @Test
        public void setUpTest(){
            when(user.getFirstName()).thenReturn("Pedro");

            assertEquals(1L, participation.getId());
            assertEquals(event,participation.getEvent());
            assertEquals(user, participation.getUser());
            assertEquals(10.0, participation.getPrice());
            assertEquals("Ticket 1", participation.getTicket());
            assertEquals(date1, participation.getBuyDate());
            assertEquals("Pedro", participation.getUser().getFirstName());
            assertEquals("Pontiga", participation.getUser().getLastName());
            assertEquals("pedro@gmail.com", participation.getUser().getEmail());
            assertEquals("Event 1", participation.getEvent().getTitle());
            assertEquals(10.0, participation.getEvent().getPrice());
            assertEquals(Participation.class, participation.getClass());

        }

        @Test
        public void getEventTest(){
            Event event = participation.getEvent();
            Event event2 = participation2.getEvent();

            assertNotNull(event);
            assertNull(event2);
            assertEquals(10.0, participation.getEvent().getPrice());
            assertEquals("Event 1", event.getTitle());
        }


        @Test
        public void getOwnerTest(){
            User owner = participation.getUser();
            User owner2 = participation2.getUser();

            assertNotNull(owner);
            assertNull(owner2);
            assertEquals("pedro@gmail.com", owner.getEmail());
            assertEquals("Pontiga", owner.getLastName());
        }

        @Test
        public void sponsorshipEqualsMethodPositiveTest(){
            assertTrue(participation.equals((Participation) participation));
            assertEquals(participation.hashCode(), participation.hashCode());
        }

        @Test
        public void sponsorshipEqualsMethodNegativeTest(){
            assertFalse(participation.equals((Participation) participation2));
            assertFalse(participation2.equals((Participation)participation));
            assertNotEquals(participation.hashCode(), participation2.hashCode());
        }

}
