package com.eventus.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.eventus.backend.models.Coordinates;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoordinatesModelTests {

    Coordinates coordinates;

    @BeforeEach                                         
    void setUp() {
        coordinates = new Coordinates();
    }
    
    @Test
    public void setUpCordinates() {
        coordinates = new Coordinates("0.0", "0.0");
        assertEquals(coordinates.getLatitude(), 0.);
        assertEquals(coordinates.getLongitude(), 0.);
    }

    @Test
    public void setUpCordinatesMaxValue() {
        coordinates.setLatitude(Double.MAX_VALUE);
        coordinates.setLongitude(Double.MAX_VALUE);
        assertEquals(coordinates.getLatitude(), Double.MAX_VALUE);
        assertEquals(coordinates.getLongitude(), Double.MAX_VALUE);
    }

    @Test
    public void setUpCordinatesMinValue() {
        coordinates.setLatitude(Double.MIN_VALUE);
        coordinates.setLongitude(Double.MIN_VALUE);
        assertEquals(coordinates.getLatitude(), Double.MIN_VALUE);
        assertEquals(coordinates.getLongitude(), Double.MIN_VALUE);
    }

    @Test
    public void setUpCordinatesOverflow() {
        coordinates.setLatitude(Double.MAX_VALUE+1.);
        coordinates.setLongitude(Double.MAX_VALUE+1.);
        assertEquals(coordinates.getLatitude(), Double.MAX_VALUE);
        assertEquals(coordinates.getLongitude(), Double.MAX_VALUE);
    }

    @Test
    public void setUpCordinatesUnderflow() {
        coordinates.setLatitude(-Double.MAX_VALUE-1.);
        coordinates.setLongitude(-Double.MAX_VALUE-1.);
        assertEquals(coordinates.getLatitude(), -Double.MAX_VALUE);
        assertEquals(coordinates.getLongitude(), -Double.MAX_VALUE);
    }

}
