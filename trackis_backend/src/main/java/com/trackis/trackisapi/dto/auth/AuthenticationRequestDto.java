package com.trackis.trackisapi.dto.auth;

public record AuthenticationRequestDto(
        String username,
        String password
) {
}
