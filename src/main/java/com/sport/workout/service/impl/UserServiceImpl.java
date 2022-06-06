package com.sport.workout.service.impl;

import com.sport.workout.model.User;
import com.sport.workout.repository.UserRepository;
import com.sport.workout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Optional<User> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> findUser(String email) {
        return userRepository.findUser(email);
    }
}
