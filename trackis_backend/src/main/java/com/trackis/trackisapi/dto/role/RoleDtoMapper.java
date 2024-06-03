package com.trackis.trackisapi.dto.role;

import com.trackis.trackisapi.entity.role.Role;

public interface RoleDtoMapper {

    default String mapRoleToString(Role role) {
        return role.getName().name();
    }
}
