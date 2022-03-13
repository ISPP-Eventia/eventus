package com.eventus.backend.services;

import com.eventus.backend.models.User;
import com.eventus.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveUser(User user) throws DataAccessException {
        userRepository.save(user);
    }

    public Page<User> findAllUsers(Pageable p) {
        return userRepository.findAll(p);
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
