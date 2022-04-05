package com.eventus.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import com.eventus.backend.models.User;
import com.eventus.backend.services.UserService;

import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests {

    @Autowired
    protected UserService userService;
    User user;

    @Test
    @Transactional
    public void shouldPaginateUsers() {
        Pageable page = PageRequest.of(0,2);
        int number = userService.findAllUsers(page).size();
        assertEquals(number,2);
    }

    @Test
    @Transactional
    public void findExistingUserByIdTest() {
       User user = userService.findUserById(1l);
       
       assertTrue(user != null);
       assertEquals(user.getFirstName(), "Juan");
       assertEquals(user.getLastName(), "Rodriguez");
    }

    @Test
    @Transactional
    public void findUnExistingUserByIdTest() {
       User user = userService.findUserById(0l);
       
       assertTrue(user == null);
    }

    @Test
    @Transactional
    public void deleteUserTest() {
        User deletedUser = userService.findUserById(2l);
        userService.deleteUser(2l, deletedUser);
        deletedUser = userService.findUserById(2l);
        
        assertTrue(deletedUser == null);
    }

    @Test
    @Transactional
    public void createNewUserTest() { 
        user = new User();
        user.setFirstName("Pepe");
        user.setLastName("Rodriguez");
        user.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
        user.setEmail("peperod@email.example");
        user.setPassword("$up3r$€cr3tP4s$w0rd");
        userService.saveUser(user);

        Optional<User> userFound = userService.findByEmail("peperod@email.example");
        
        assertTrue(userFound.isPresent());
        assertEquals(user, userFound.get());
    }

    @Test
    @Transactional
    public void loginTest(){
        user = new User();
        user.setFirstName("Alvaro");
        user.setLastName("Martínez");
        user.setBirthDate(LocalDate.of(2000, Month.FEBRUARY, 14));
        user.setEmail("alvaro@example.com");
        user.setPassword("$up3r$€cr3tP4s$w0rd");

        userService.saveUser(user);
        Optional<String> token = userService.login("alvaro@example.com", "$up3r$€cr3tP4s$w0rd");
        
        assertTrue(token.isPresent());
        assertTrue(userService.findByToken(token.get()).isPresent());
        assertEquals(userService.findByToken(token.get()).get(), user);

    }
}
