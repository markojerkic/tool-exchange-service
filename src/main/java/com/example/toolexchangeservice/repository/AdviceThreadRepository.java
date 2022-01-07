package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.AdviceThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdviceThreadRepository extends JpaRepository<AdviceThread, Long>,
        PagingAndSortingRepository<AdviceThread, Long> {
}
