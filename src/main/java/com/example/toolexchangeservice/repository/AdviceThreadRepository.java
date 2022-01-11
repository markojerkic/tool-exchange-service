package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.AdviceThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AdviceThreadRepository extends JpaRepository<AdviceThread, Long>,
        PagingAndSortingRepository<AdviceThread, Long> {

    @Query("update AdviceThread set numAdvices=:comments where id = :id")
    @Transactional
    @Modifying
    void updateNumComments(@Param("comments") Integer comments,
                           @Param("id") Long id);
}
