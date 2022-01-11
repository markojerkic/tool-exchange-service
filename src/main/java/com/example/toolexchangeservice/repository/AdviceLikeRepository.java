package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.Advice;
import com.example.toolexchangeservice.model.entity.AdviceIsLiked;
import com.example.toolexchangeservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdviceLikeRepository extends JpaRepository<AdviceIsLiked, Long> {
    Optional<AdviceIsLiked> findByByUser_IdAndAdvice_Id(Long userId, Long adviceId);

    boolean existsByAdviceAndByUser(Advice advice, UserDetail userDetail);

    Integer countByAdvice_Id(Long id);
}
