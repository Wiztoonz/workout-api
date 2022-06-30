package com.sport.workout.service;

import com.sport.workout.model.Role;
import com.sport.workout.model.User;
import com.sport.workout.model.UserRole;

public interface UserRoleService {

    UserRole saveUserRole(User user, Role role);

}
