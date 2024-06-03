package com.trackis.trackisapi.dto.user;

import com.trackis.trackisapi.dto.role.RoleDtoMapper;
import com.trackis.trackisapi.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper extends RoleDtoMapper {

    UserResponseDto toDto(User user);

    User toEntity(UserRequestDto userRequestDto);
}