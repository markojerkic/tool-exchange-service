package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AdRepository extends JpaRepository<AdDetail, Long>, PagingAndSortingRepository<AdDetail, Long> {
}
