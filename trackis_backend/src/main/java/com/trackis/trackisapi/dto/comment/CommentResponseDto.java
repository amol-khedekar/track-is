package com.trackis.trackisapi.dto.comment;

import com.trackis.trackisapi.dto.user.UserResponseDto;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "comments", itemRelation = "comment")
public record CommentResponseDto(
        Long id,
        String body,
        UserResponseDto author,
        Long issueId
) {
}

