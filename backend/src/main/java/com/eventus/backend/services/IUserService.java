package com.eventus.backend.services;

import com.eventus.backend.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService{

    void saveUser(User user);
    List<User> findAllUsers(Pageable p);
    User findUserById(Long id);

    Optional<String> login(final String username, final String password);
    Optional<User> findByToken(final String token);

    void update(Map<String, String> params, Long userId,User loggedUser);

    void deleteUser(Long id,User loggedUser);
}
