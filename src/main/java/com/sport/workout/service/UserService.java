package com.sport.workout.service;

import com.sport.workout.model.User;

import java.util.Optional;

public interface UserService {

    User registerUser(User user);

    Optional<User> findUser(String email);

}
