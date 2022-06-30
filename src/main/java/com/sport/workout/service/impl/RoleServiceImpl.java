package com.sport.workout.service.impl;

import com.sport.workout.model.Role;
import com.sport.workout.model.RoleName;
import com.sport.workout.repository.RoleRepository;
import com.sport.workout.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRole(RoleName roleName) {
        return roleRepository.findRole(roleName);
    }

}
