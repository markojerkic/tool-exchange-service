package com.example.toolexchangeservice.model.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private List<String> roles;
}
