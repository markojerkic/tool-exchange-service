package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.service.AuthService;
import com.example.toolexchangeservice.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/user")
@AllArgsConstructor
@CrossOrigin("*")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final AuthService authService;

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
    @GetMapping("all")
    public List<UserDetail> getUsers() {
        return this.userManagementService.getAllUsers();
    }

    @GetMapping("")
    public UserDetail getUser(@RequestParam(required = false) String username,
                              @RequestParam(required = false) Long id,
                              @RequestParam(required = false) String email ) {
        if (username != null)
            return this.userManagementService.loadUserByUsername(username);
        else if (id != null)
            return this.userManagementService.loadUserById(id);
        else if (email != null)
            return this.userManagementService.loadUserByEmail(email);
        else
            throw new UsernameNotFoundException("Korisnik nije pronađen");
    }

}
