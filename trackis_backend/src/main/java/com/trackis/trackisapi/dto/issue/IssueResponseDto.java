package com.trackis.trackisapi.dto.issue;

import com.trackis.trackisapi.dto.user.UserResponseDto;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "issues", itemRelation = "issue")
public record IssueResponseDto(
        Long id,
        String title,
        String description,
        UserResponseDto author,
        Long projectId
) {
}
