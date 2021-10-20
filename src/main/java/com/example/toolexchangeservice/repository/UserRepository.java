package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByEmail(String email);
}
