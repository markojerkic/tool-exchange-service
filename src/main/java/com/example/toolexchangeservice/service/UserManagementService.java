package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;

    /**
     * Save new user or update existing one. If updating, you must send id.
     * If id is null, then the repository will create a new user.
     * @param user Data that will be saved in repo
     * @return Saved user
     */
    public UserDetail saveUser(UserDetail user) {
        return this.userRepository.save(user);
    }

    /**
     * Return a list of all existing users saved in the database
     * @return List of all users in the database
     */
    public List<UserDetail> getAllUsers() {
        return this.userRepository.findAll();
    }
}
