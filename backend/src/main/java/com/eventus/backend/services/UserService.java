package com.eventus.backend.services;

import com.eventus.backend.models.User;
import com.eventus.backend.repositories.UserRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;

    private final JWTTokenService tokens;

    private final PasswordEncoder passwordEncoder;

    private final StripeService stripeService;

    @Autowired
    public UserService(UserRepository userRepository,JWTTokenService tokens,PasswordEncoder passwordEncoder, StripeService stripeService) {
        this.userRepository = userRepository;
        this.tokens=tokens;
        this.passwordEncoder=passwordEncoder;
        this.stripeService = stripeService;
    }

    @Transactional
    public void saveUser(User user) throws DataAccessException {
        user.setCustomerId(stripeService.createCustomer(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public void update(Map<String, String> params, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user!=null){
            user.setPassword(params.get("password"));
            user.setBirthDate(LocalDate.parse(params.get("birthDate")));
            user.setEmail(params.get("email"));
            user.setFirstName(params.get("firstName"));
            userRepository.save(user);
        }
    }
}
