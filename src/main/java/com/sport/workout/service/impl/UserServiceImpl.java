package com.sport.workout.service.impl;

import com.sport.workout.exseption.RoleNotFoundException;
import com.sport.workout.exseption.UserAlreadyExistsException;
import com.sport.workout.exseption.UserNotFoundException;
import com.sport.workout.model.Role;
import com.sport.workout.model.RoleName;
import com.sport.workout.model.User;
import com.sport.workout.repository.RoleRepository;
import com.sport.workout.repository.UserRepository;
import com.sport.workout.service.UserRoleService;
import com.sport.workout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Optional<User> registerUser(User user) {
        User persistedUser;
        Role role = Optional.ofNullable(roleRepository.findRole(RoleName.USER)).orElseThrow(() -> new RoleNotFoundException("Role not found!"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            persistedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(String.format("User with email: %s already exists!", user.getEmail()));
        }
        userRoleService.saveUserRole(user, role);
        return Optional.of(persistedUser);
    }

    @Override
    public Optional<User> findUser(String email) {
        return Optional.ofNullable(userRepository.findUser(email))
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s is not found!", email)));
    }

}
