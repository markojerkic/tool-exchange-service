package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("users")
@AllArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

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
}
