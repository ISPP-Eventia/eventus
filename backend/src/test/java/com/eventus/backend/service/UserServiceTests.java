package com.eventus.backend.service;

import com.eventus.backend.models.User;
import com.eventus.backend.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    protected UserService userService;

    @Test
    @Transactional
    public void shouldPaginateUsers() {
        Pageable page = PageRequest.of(0,2);
        int number = userService.findAllUsers(page).size();
        assertEquals(number,2);

    }
}
