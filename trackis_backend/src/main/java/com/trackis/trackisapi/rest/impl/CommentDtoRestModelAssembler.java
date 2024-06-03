package com.trackis.trackisapi.rest.impl;

import com.trackis.trackisapi.dto.comment.CommentResponseDto;
import com.trackis.trackisapi.rest.RestModelAssembler;
import com.trackis.trackisapi.controller.UserController;
import com.trackis.trackisapi.controller.CommentController;
import com.trackis.trackisapi.controller.IssueController;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentDtoRestModelAssembler implements RestModelAssembler<CommentResponseDto> {

    @Override
    public @NonNull EntityModel<CommentResponseDto> toModel(CommentResponseDto commentResponseDto) {
        Long commentId = commentResponseDto.id();
        Long issueId = commentResponseDto.issueId();
        String username = commentResponseDto.author().username();

        return EntityModel.of(commentResponseDto,
                linkTo(methodOn(CommentController.class).getComment(commentId)).withSelfRel(),
                linkTo(methodOn(UserController.class).getUser(username)).withRel("author"),
                linkTo(methodOn(IssueController.class).getIssue(issueId)).withRel("issue"));
    }
}
