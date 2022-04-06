package com.eventus.backend.services;

import com.eventus.backend.models.User;
import com.eventus.backend.repositories.UserRepository;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;

    private final JWTTokenService tokens;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository,JWTTokenService tokens,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokens=tokens;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional
    public void saveUser(User user) throws DataAccessException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAdmin(false);
        userRepository.save(user);
    }

    public List<User> findAllUsers(Pageable p) {
        return userRepository.findAll(p);
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String username){
        return userRepository.findByEmail(username);

    }

    @Override
    public Optional<String> login(final String email, final String password) {
        return userRepository
                .findByEmail(email)
                .filter(user -> passwordEncoder.matches(password,user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("email", email)));
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("email"))
                .flatMap(userRepository::findByEmail);
    }

    @Transactional
    @Override
    public void update(Map<String, String> params, Long userId, User loggedUser) {
        Validate.isTrue(loggedUser.getId().equals(userId) || loggedUser.isAdmin(), "You can't update other users");
        User user = userRepository.findById(userId).orElse(null);
        Validate.notNull(user, "User not found");
        if(StringUtils.isNotBlank(params.get("password"))){
            user.setPassword(passwordEncoder.encode(params.get("password")));
        }
        user.setBirthDate(Instant.parse(params.get("birthDate")).atZone(ZoneId.systemDefault()).toLocalDate());
        user.setFirstName(params.get("firstName"));
        user.setLastName(params.get("lastName"));
        Validate.isTrue(user.getFirstName().length() < 20, "First name must be less than 20 characters");
        Validate.isTrue(user.getLastName().length() < 40, "Last name must be less than 40 characters");
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId, User loggedUser) {
        Validate.isTrue(loggedUser.getId().equals(userId) || loggedUser.isAdmin(), "You can't delete other users");
        User user = userRepository.findById(userId).orElse(null);
        Validate.notNull(user, "User not found");
        userRepository.delete(user);
    }
}
