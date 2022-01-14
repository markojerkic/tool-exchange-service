package com.example.toolexchangeservice.auth;

import com.example.toolexchangeservice.config.auth.JwtUtils;
import com.example.toolexchangeservice.model.auth.dto.LoginRequest;
import com.example.toolexchangeservice.service.AuthService;
import com.example.toolexchangeservice.service.UserManagementService;
import io.jsonwebtoken.ClaimJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthTest {
    @Mock
    private UserManagementService userManagementService;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    @Test
    public void refreshTokenTest() {

        assertThrows(IllegalArgumentException.class, () -> authService.refreshToken("34"));
    }
}
