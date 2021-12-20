package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
