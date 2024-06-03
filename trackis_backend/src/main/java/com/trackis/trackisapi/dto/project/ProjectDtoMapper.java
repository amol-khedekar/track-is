package com.trackis.trackisapi.dto.project;

import com.trackis.trackisapi.dto.role.RoleDtoMapper;
import com.trackis.trackisapi.entity.Project;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectDtoMapper extends RoleDtoMapper {

    ProjectResponseDto toDto(Project project);

    Project toEntity(ProjectRequestDto projectRequestDto);
}
