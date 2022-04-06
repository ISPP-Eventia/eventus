package com.eventus.backend.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.eventus.backend.models.Coordinates;
import com.eventus.backend.models.Location;
import com.eventus.backend.models.User;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LocationModelTests {
	
	@Mock
	User owner;
	
	@Mock
	Coordinates coordinates;
	
	Location location;
	Location location2;
	
	@Before
	public void setUp() {
		when(owner.getUsername()).thenReturn("josrompoz1");
		when(owner.getEmail()).thenReturn("josrompoz1@gmail.com");
		when(coordinates.getLatitude()).thenReturn(12.0);
		when(coordinates.getLongitude()).thenReturn(34.0);
		
		location = new Location();
		location.setId(1L);
		location.setName("Pista de futbol");
		location.setDescription("Descripcion");
		location.setPrice(100.0);
		location.setOwner(owner);
		location.setCoordinates(coordinates);
		
		location2 = new Location();
	}
	
	@Test
	public void setUpLocation() {
		assertEquals(location.getId(), 1L);
		assertEquals(location.getName(), "Pista de futbol");
		assertEquals(location.getDescription(), "Descripcion");
		assertEquals(location.getPrice(), 100.0);
		assertEquals(location.getOwner().getUsername(), "josrompoz1");
		assertEquals(location.getCoordinates().getLongitude(), 34.0);
	}
	
	@Test
	public void getUserTest() {
		User user = location.getOwner();
		User user2 = location2.getOwner();
		
		assertFalse(user==null);
		assertTrue(user2==null);
		assertEquals(location.getOwner().getEmail(), "josrompoz1@gmail.com");
	}
	
	@Test
	public void getCoordinatesTest() {
		Coordinates coordinates = location.getCoordinates();
		Coordinates coordinates2 = location2.getCoordinates();
		
		assertFalse(coordinates==null);
		assertTrue(coordinates2==null);
		assertEquals(coordinates.getLatitude(), 12.0);
		assertEquals(coordinates.getLongitude(), 34.0);
	}
	
	@Test
	public void locationEqualsMethodPositiveTest() {
		assertTrue(location.equals((Location) location));
		assertEquals(location.hashCode(), location.hashCode());
	}
	
	@Test
	public void locationEqualsMethodNegativeTest() {
		assertFalse(location.equals((Location) location2));
		assertFalse(location2.equals((Location) location));
		assertNotEquals(location.hashCode(), location2.hashCode());
	}
	
	@Test
	public void locationToStringMethodTest() {
		assertTrue(location.toString().contains("name='"+location.getName()+"'"));
		assertTrue(location2.toString().contains("name='"+location2.getName()+"'"));
	}

}
