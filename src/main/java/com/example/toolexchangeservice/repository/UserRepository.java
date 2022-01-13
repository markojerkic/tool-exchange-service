package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetail, Long>, PagingAndSortingRepository<UserDetail, Long>,
        JpaSpecificationExecutor<UserDetail> {
    Optional<UserDetail> findByEmail(String email);
    Optional<UserDetail> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<UserDetail> findAllByIsBestHandyman(boolean isBest);

}
