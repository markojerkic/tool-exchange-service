package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.EmailAlreadyExistsException;
import com.example.toolexchangeservice.config.exception.EmailNotFoundException;
import com.example.toolexchangeservice.config.exception.IdNotFoundException;
import com.example.toolexchangeservice.config.exception.UsernameAlreadyExistsException;
import com.example.toolexchangeservice.model.auth.Role;
import com.example.toolexchangeservice.model.dto.UserPreviewDTO;
import com.example.toolexchangeservice.model.dto.UserUpdateDTO;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.model.location.Result;
import com.example.toolexchangeservice.repository.UserRepository;
import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email adresa se već koristi");
        }
        if (this.userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Korisničko ime se već koristi");
        }

        Result location = user.getLocationSearchResult();
        user.setFormattedAddress(location.getFormattedAddress());
        user.setLat(location.getGeometry().getLocation().getLat());
        user.setLng(location.getGeometry().getLocation().getLng());

        user.setRoles(ImmutableSet.of(Role.USER));

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    /**
     * Searches the database for user with the given username.
     * @param username Unique username by which the database is queried
     * @return UserDetails class with data of the user with the given username
     * @throws UsernameNotFoundException Throws if database does not contain user with the username
     */
    @Override
    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Korisnik s korisničkim imenom " + username + " nije pronađen"));
    }

    public UserDetail loadUserById(Long id) throws IdNotFoundException {
        return this.userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Korisnik s korisničkim id " + id + " nije pronađen"));
    }

    public UserDetail loadUserByEmail(String email) throws EmailNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Korisnik s email adresom " + email + " nije pronađen"));
    }

    @Secured("ROLE_ADMIN")
    public Page<UserPreviewDTO> getUserPage(Pageable pageable, Optional<String> username,
                                            Optional<String> email, Optional<Boolean> isBlocked) {
        return this.userRepository.findAll(this.createQuerySpecification(username, email, isBlocked), pageable).map(this::mapToDto);
    }

    private UserPreviewDTO mapToDto(UserDetail user) {
        UserPreviewDTO dto = new UserPreviewDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setIsDisabled(user.getIsDisabled());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setFormattedAddress(user.getFormattedAddress());
        dto.setAverageRating(user.getAverageRating());

        return dto;
    }

    private Specification<UserDetail> createQuerySpecification(Optional<String> username, Optional<String> email,
                                                               Optional<Boolean> isBlocked) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            username.ifPresent(un -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(
                    root.get("username")), "%" + un.toUpperCase() + "%")));
            email.ifPresent(em -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(
                    root.get("email")), "%" + em.toUpperCase() + "%")));
            isBlocked.ifPresent(ib -> predicates.add(criteriaBuilder.equal(root.get("isDisabled"), ib)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @Secured("ROLE_ADMIN")
    public void blockUserById(Long id) throws IdNotFoundException {
        UserDetail data = this.userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Korisnik s korisničkim id " + id + " nije pronađen"));
        data.setIsDisabled(!data.getIsDisabled());
        this.userRepository.save(data);
    }

    public void updateUserPrivateInformationById(UserUpdateDTO data) throws IdNotFoundException {
        UserDetail update = this.userRepository.findById(data.getId()).orElseThrow(() ->
                new UsernameNotFoundException("Korisnik s korisničkim id " + data.getId() + " nije pronađen"));
        update.setFirstName(data.getFirstName());
        update.setLastName(data.getLastName());
        update.setPhoneNumber(data.getPhoneNumber());
        this.userRepository.save(update);
    }

    @Async
    public void updateAverageRating(UserDetail aboutUser, Float averageRating) {
        aboutUser.setAverageRating(averageRating);
        this.userRepository.save(aboutUser);
    }

    public UserPreviewDTO getUserByUsername(String username) {
        return this.mapToDto(this.loadUserByUsername(username));
    }
}
