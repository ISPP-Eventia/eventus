package com.eventus.backend.services;

import com.eventus.backend.models.User;
import com.eventus.backend.repositories.UserRepository;
import com.google.common.collect.ImmutableMap;
import com.stripe.exception.StripeException;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final JWTTokenService tokens;

    private final PasswordEncoder passwordEncoder;

    private final StripeService stripeService;

    @Autowired
    public UserService(UserRepository userRepository, JWTTokenService tokens, PasswordEncoder passwordEncoder,
            StripeService stripeService) {
        this.userRepository = userRepository;
        this.tokens = tokens;
        this.passwordEncoder = passwordEncoder;
        this.stripeService = stripeService;
    }

    @Transactional
    public void saveUser(User user) throws DataAccessException, StripeException {
        user.setCustomerId(stripeService.createCustomer(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAdmin(false);
        userRepository.save(user);
    }

    public List<User> findAllUsers(Pageable p) {
        return userRepository.findAll(p);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);

    }

    @Override
    public Optional<String> login(final String email, final String password) {
        return userRepository
                .findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
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
        Validate.isTrue(loggedUser.getId().equals(userId) || loggedUser.isAdmin(), "No puedes modificar a otros usuarios");
        User user = userRepository.findById(userId).orElse(null);
        Validate.isTrue(user!=null, "Usuario no encontrado");
        if (StringUtils.isNotBlank(params.get("password"))) {
            user.setPassword(passwordEncoder.encode(params.get("password")));
        }
        user.setBirthDate(Instant.parse(params.get("birthDate")).atZone(ZoneId.systemDefault()).toLocalDate());
        user.setFirstName(params.get("firstName"));
        user.setLastName(params.get("lastName"));
        Validate.isTrue(user.getFirstName().length() < 20, "El nombre no puede tener más de 20 caracteres");
        Validate.isTrue(user.getLastName().length() < 40, "El apellido no puede tener más de 40 caracteres");
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId, User loggedUser) {
        Validate.isTrue(loggedUser.getId().equals(userId) || loggedUser.isAdmin(), "No puedes eliminar a otros usuarios");
        User user = userRepository.findById(userId).orElse(null);
        Validate.isTrue(user!=null, "Usuarios no encontrado");
        userRepository.delete(user);
    }
}
