package com.eventus.backend.services;

import com.eventus.backend.models.User;
import com.eventus.backend.repositories.UserRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.eventus.backend.configuration.TokenAuthenticationFilter.TOKEN;

@Service
public class UserService implements IUserService{

    private UserRepository userRepository;

    private JWTTokenService tokens;

    @Autowired
    public UserService(UserRepository userRepository,JWTTokenService tokens) {
        this.userRepository = userRepository;
        this.tokens=tokens;
    }

    @Transactional
    public void saveUser(User user) throws DataAccessException {
        userRepository.save(user);
    }

    public List<User> findAllUsers(Pageable p) {
        return userRepository.findAll(p);
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String username){
        return userRepository.findByEmail(username);

    }

    @Override
    public Optional<String> login(final String username, final String password) {
        return userRepository
                .findByEmail(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("username", username)));
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(userRepository::findByEmail);
    }

}
