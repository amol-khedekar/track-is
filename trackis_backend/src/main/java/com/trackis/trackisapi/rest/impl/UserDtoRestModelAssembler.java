package com.trackis.trackisapi.rest.impl;

import com.trackis.trackisapi.dto.user.UserResponseDto;
import com.trackis.trackisapi.rest.RestModelAssembler;
import com.trackis.trackisapi.controller.ProjectController;
import com.trackis.trackisapi.controller.UserController;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDtoRestModelAssembler implements RestModelAssembler<UserResponseDto> {

    @Override
    public @NonNull EntityModel<UserResponseDto> toModel(UserResponseDto userResponseDto) {
        String username = userResponseDto.username();

        return EntityModel.of(userResponseDto,
                linkTo(methodOn(UserController.class).getUser(username)).withSelfRel(),
                linkTo(methodOn(ProjectController.class).getProjectsPage(username, null, Pageable.unpaged())).withRel("owned-projects").expand(),
                linkTo(methodOn(ProjectController.class).getProjectsPage(null, username, Pageable.unpaged())).withRel("shared-projects").expand());
    }
}
