package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.config.auth.JwtUtils;
import com.example.toolexchangeservice.model.auth.dto.JwtResponse;
import com.example.toolexchangeservice.model.auth.dto.LoginRequest;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping("api/auth")
@AllArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    /**
     * Api operation for saving a new user or updating existing one
     * @param user Data that wil be stored in database
     * @return The saved user info
     */
    @PostMapping
    public UserDetail createNewUser(@RequestBody UserDetail user) {
        return this.userManagementService.saveUser(user);
    }

    /**
     * Api operation that returns a list of all existing users saved in the database
     * @return List of all existing users in database
     */
    @GetMapping
    public List<UserDetail> getAllUsers() {
        return this.userManagementService.getAllUsers();
    }

    @GetMapping("user")
    public UserDetail getUserById(@RequestParam("id") UserDetail user) {
        return user;
    }

    @PutMapping()
    public JwtResponse logIn(@RequestBody LoginRequest loginRequest) {
        Authentication auth = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = this.jwtUtils.generateToken(auth);

        UserDetails user = (UserDetails) auth.getPrincipal();
        List<String> roles = Arrays.asList("ROLE_USER");
        return new JwtResponse(jwt, jwt, roles);
    }
}
