package com.sport.workout.service.impl;

import com.sport.workout.model.Role;
import com.sport.workout.model.User;
import com.sport.workout.model.UserRole;
import com.sport.workout.repository.UserRoleRepository;
import com.sport.workout.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public UserRole saveUserRole(User user, Role role) {
        return userRoleRepository.save(new UserRole(null, user, role));
    }

}
