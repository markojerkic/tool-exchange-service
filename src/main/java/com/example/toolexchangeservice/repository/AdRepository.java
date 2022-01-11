package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.AdDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdRepository extends JpaRepository<AdDetail, Long>, PagingAndSortingRepository<AdDetail, Long>,
        JpaSpecificationExecutor<AdDetail> {
}
