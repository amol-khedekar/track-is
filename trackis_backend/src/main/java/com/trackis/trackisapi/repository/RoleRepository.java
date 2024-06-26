package com.trackis.trackisapi.repository;

import com.trackis.trackisapi.entity.role.Role;
import com.trackis.trackisapi.entity.role.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(RoleType name);
}
