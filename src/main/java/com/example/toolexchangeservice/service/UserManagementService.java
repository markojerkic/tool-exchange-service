package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserManagementService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Save new user. User's password will be hashed upon saving in database.
     * If id is null, then the repository will create a new user.
     * @param user Data that will be saved in repo
     * @return Saved user
     */
    public UserDetail saveUser(UserDetail user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    /**
     * Return a list of all existing users saved in the database
     * @return List of all users in the database
     */
    public List<UserDetail> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(s).orElseThrow(() ->
                new UsernameNotFoundException("Korisnik s emailom " + s + " nije pronađen"));
    }
}
