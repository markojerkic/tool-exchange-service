package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdviceRepository extends JpaRepository<Advice, Long>,
        PagingAndSortingRepository<Advice, Long> {

    Page<Advice> findAllByParentThread_Id(Pageable pageable, Long id);
}
