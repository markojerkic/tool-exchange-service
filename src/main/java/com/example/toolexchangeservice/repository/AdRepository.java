package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdRepository extends JpaRepository<AdDetail, Long> {
    Optional<UserDetail> findByTitle(String title);
    boolean existsByTitle(String title);
}
