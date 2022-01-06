package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestRepository extends JpaRepository<Request, Long>, PagingAndSortingRepository<Request, Long> {
}
