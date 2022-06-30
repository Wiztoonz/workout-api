package com.sport.workout.repository;

import com.sport.workout.model.Role;
import com.sport.workout.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRole(@Param("roleName") RoleName roleName);

}
