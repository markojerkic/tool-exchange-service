package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.UserRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<UserRating, Long>, PagingAndSortingRepository<UserRating, Long> {
    List<UserRating> findAllByAboutUser_UsernameOrderByLastModifiedDesc(String id, Pageable pageable);
}
