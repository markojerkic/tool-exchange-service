package com.example.toolexchangeservice.auth;

import com.example.toolexchangeservice.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthLoggedTest {
    @Autowired
    private AuthService authService;

    @Test
    public void loggedUserTest() {
        assertThrows(Exception.class, () -> authService.getLoggedInUser());
    }
}
