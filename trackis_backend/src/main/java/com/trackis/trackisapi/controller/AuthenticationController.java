package com.trackis.trackisapi.controller;

import com.trackis.trackisapi.dto.auth.AuthenticationRequestDto;
import com.trackis.trackisapi.dto.auth.AuthenticationResponseDto;
import com.trackis.trackisapi.dto.token.AccessTokenRequestDto;
import com.trackis.trackisapi.dto.token.AccessTokenResponseDto;
import com.trackis.trackisapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDto> signIn(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        Map<String, String> tokens = new HashMap<>(authenticationService.authenticateUser((authenticationRequestDto)));
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(tokens.get("accessToken"), tokens.get("refreshToken"));
        return ResponseEntity.ok(authenticationResponseDto);
    }

    @PostMapping(path = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccessTokenResponseDto> refreshAccessToken(@RequestBody AccessTokenRequestDto accessTokenRequestDto) {
        String accessToken = authenticationService.refreshAccessToken(accessTokenRequestDto);
        return ResponseEntity.ok(new AccessTokenResponseDto(accessToken));
    }
}
