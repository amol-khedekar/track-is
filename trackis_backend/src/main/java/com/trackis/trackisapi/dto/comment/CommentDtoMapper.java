package com.trackis.trackisapi.dto.comment;

import com.trackis.trackisapi.dto.role.RoleDtoMapper;
import com.trackis.trackisapi.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentDtoMapper extends RoleDtoMapper {

    @Mapping(source = "comment.issue.id", target = "issueId")
    CommentResponseDto toDto(Comment comment);

    Comment toEntity(CommentRequestDto commentRequestDto);
}
