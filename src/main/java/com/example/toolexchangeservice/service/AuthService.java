package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.auth.JwtUtils;
import com.example.toolexchangeservice.model.auth.dto.JwtResponse;
import com.example.toolexchangeservice.model.auth.dto.LoginRequest;
import com.example.toolexchangeservice.model.entity.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserManagementService userManagementService;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    /**
     * Check credentials. Builds JWT access and refresh tokens.
     * @param loginRequest Contains credentials: username and password
     * @return Response containing JWT access and refresh tokens and username
     */
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication auth = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String accessToken = this.jwtUtils.generateAccessToken(auth);
        String refreshToken = this.jwtUtils.generateRefreshToken(auth);

        UserDetails user = (UserDetails) auth.getPrincipal();
        List<String> roles = Arrays.asList("ROLE_USER");
        return new JwtResponse(accessToken, refreshToken, user.getUsername(), roles);
    }

    public JwtResponse refreshToken(String refreshToken) {
        if (!this.jwtUtils.validateJwtToken(refreshToken)) {
            throw new IllegalArgumentException("Refresh token is not valid");
        }
        String username = this.jwtUtils.getUsernameFromJwtToken(refreshToken);
        UserDetails user = this.userManagementService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());
        List<String> roles = Arrays.asList("ROLE_USER");

        return new JwtResponse(this.jwtUtils.generateAccessToken(auth),
                this.jwtUtils.generateRefreshToken(auth), username, roles);
    }

    public UserDetail getLoggedInUser() {
        return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
