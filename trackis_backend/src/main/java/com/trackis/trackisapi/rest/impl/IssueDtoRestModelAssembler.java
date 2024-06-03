package com.trackis.trackisapi.rest.impl;

import com.trackis.trackisapi.controller.UserController;
import com.trackis.trackisapi.rest.RestModelAssembler;
import com.trackis.trackisapi.controller.IssueController;
import com.trackis.trackisapi.controller.ProjectController;
import com.trackis.trackisapi.dto.issue.IssueResponseDto;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class IssueDtoRestModelAssembler implements RestModelAssembler<IssueResponseDto> {

    @Override
    public @NonNull EntityModel<IssueResponseDto> toModel(IssueResponseDto issueResponseDto) {
        Long issueId = issueResponseDto.id();
        String username = issueResponseDto.author().username();
        Long projectId = issueResponseDto.projectId();

        return EntityModel.of(issueResponseDto,
                linkTo(methodOn(IssueController.class).getIssue(issueId)).withSelfRel(),
                linkTo(methodOn(UserController.class).getUser(username)).withRel("author"),
                linkTo(methodOn(ProjectController.class).getProject(projectId)).withRel("project"),
                linkTo(methodOn(IssueController.class).getIssueComments(issueId, Pageable.unpaged())).withRel("comments").expand());
    }
}
