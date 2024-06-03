package com.trackis.trackisapi.service;

import com.trackis.trackisapi.dto.auth.AuthenticationRequestDto;
import com.trackis.trackisapi.dto.token.AccessTokenRequestDto;
import com.trackis.trackisapi.exception.JwtTokenException;
import com.trackis.trackisapi.security.jwt.JwtTokenService;
import com.trackis.trackisapi.security.jwt.TokenType;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public Map<String, String> authenticateUser(AuthenticationRequestDto authenticationRequestDto) {
        Authentication authentication = getAuthenticationFromUsernamePassword(
                authenticationRequestDto.username(),
                authenticationRequestDto.password()
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", jwtTokenService.generateAccessToken(userDetails));
        tokens.put("refreshToken", jwtTokenService.generateRefreshToken(userDetails));
        return tokens;
    }

    public Authentication getAuthenticationFromUsernamePassword(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    public String refreshAccessToken(AccessTokenRequestDto accessTokenRequestDto) {
        try {
            jwtTokenService.verifyToken(accessTokenRequestDto.refreshToken(), TokenType.REFRESH);
        } catch (JwtException e) {
            throw new JwtTokenException("Refresh token is expired or invalid");
        }
        String username = jwtTokenService.extractUsernameFromToken(accessTokenRequestDto.refreshToken(), TokenType.REFRESH);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenService.generateAccessToken(userDetails);
    }
}
