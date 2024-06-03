package com.trackis.trackisapi.dto.issue;

import com.trackis.trackisapi.dto.role.RoleDtoMapper;
import com.trackis.trackisapi.entity.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IssueDtoMapper extends RoleDtoMapper {

    @Mapping(source = "issue.project.id", target = "projectId")
    IssueResponseDto toDto(Issue issue);

    Issue toEntity(IssueRequestDto issueRequestDto);
}
