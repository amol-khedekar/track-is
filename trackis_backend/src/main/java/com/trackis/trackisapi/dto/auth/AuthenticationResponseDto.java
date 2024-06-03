package com.trackis.trackisapi.dto.auth;

public record AuthenticationResponseDto(
        String accessToken,
        String refreshToken
) {
}
