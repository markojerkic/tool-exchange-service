package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.auth.dto.JwtResponse;
import com.example.toolexchangeservice.model.auth.dto.LoginRequest;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestParam String token) {
        return this.authService.refreshToken(token);
    }

    @GetMapping
    public UserDetail getLoggedInUser() {
        return this.authService.getLoggedInUser();
    }

    @PutMapping()
    public JwtResponse logIn(@RequestBody LoginRequest loginRequest) {
        return this.authService.login(loginRequest);
    }
}
