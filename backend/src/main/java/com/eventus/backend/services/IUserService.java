package com.eventus.backend.services;

import com.eventus.backend.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService{

    void saveUser(User user);
    List<User> findAllUsers(Pageable p);
    User findUserById(Long id);
    void deleteUser(Long id);

}
