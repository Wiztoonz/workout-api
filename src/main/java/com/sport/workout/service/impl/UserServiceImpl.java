package com.sport.workout.service.impl;

import com.sport.workout.model.Role;
import com.sport.workout.model.RoleName;
import com.sport.workout.model.User;
import com.sport.workout.repository.RoleRepository;
import com.sport.workout.repository.UserRepository;
import com.sport.workout.service.UserRoleService;
import com.sport.workout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Role role = roleRepository.findRole(RoleName.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User persistedUser = userRepository.save(user);
        userRoleService.saveUserRole(user, role);
        return Optional.of(persistedUser);
    }

    @Override
    public Optional<User> findUser(String email) {
        return userRepository.findUser(email);
    }

}
