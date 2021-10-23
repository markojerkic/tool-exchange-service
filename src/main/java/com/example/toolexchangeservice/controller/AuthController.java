package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.auth.dto.JwtResponse;
import com.example.toolexchangeservice.model.auth.dto.LoginRequest;
import com.example.toolexchangeservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController()
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestParam String token) {
        return this.authService.refreshToken(token);
    }

    @PutMapping()
    public JwtResponse logIn(@RequestBody LoginRequest loginRequest) {
        return this.authService.login(loginRequest);
    }
}
