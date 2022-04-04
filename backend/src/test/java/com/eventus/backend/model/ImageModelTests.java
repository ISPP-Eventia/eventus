package com.eventus.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;

import com.eventus.backend.models.Image;
import com.eventus.backend.models.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ImageModelTests {

    @Mock
    User userDummy;
    Image image;
    Image image2;
    static LocalDate fecha = LocalDate.of(2022, Month.APRIL, 4);



    @Before
    public void setUp(){
        image = new Image();

        image.setId(1l);
        image.setTitle("Image 1");
        image.setPath("/home/user/images");
        image.setDescription("description");
        image.setUploadDate(fecha);
        image.setUploadedBy(userDummy);

        image2 = new Image();

        image2.setId(2l);
        image2.setTitle("Image 2");
        image2.setPath("/home/user2/images");
        image2.setDescription("description2");
        image2.setUploadDate(fecha);
        image2.setUploadedBy(userDummy);
    }

    @Test
    public void setUpImageTest(){
        when(userDummy.getFirstName()).thenReturn("Pepe");
        when(userDummy.getLastName()).thenReturn("Rodriguez");
        
        assertEquals(image.getId(), 1l);
        assertEquals(image.getTitle(), "Image 1");
        assertEquals(image.getPath(), "/home/user/images");
        assertEquals(image.getDescription(), "description");
        assertEquals(image.getUploadDate(), fecha);
        assertEquals(image.getUploadedBy().getFirstName(), "Pepe");
        assertEquals(image.getUploadedBy().getLastName(), "Rodriguez");
    }

    @Test
    public void imageEqualsMethodPositiveTest(){
        assertTrue(image.equals(image));
        assertEquals(image.hashCode(), image.hashCode());
    }

    @Test
    public void imageEqualsMethodNegativeTest(){
        assertFalse(image.equals((Object)image2));
        assertFalse(image2.equals((Object)image));
        assertNotEquals(image.hashCode(), image2.hashCode());
    }

    @Test
    public void imageEqualsNullTest(){
        assertFalse(image.equals(null));
    }
}
