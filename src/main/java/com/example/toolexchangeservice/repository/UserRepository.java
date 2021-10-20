package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetail, Long> {
}
