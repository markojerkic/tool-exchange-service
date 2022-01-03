package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    List<Image> findAllByAdvert_Id(Long id);
    void deleteAllByAdvert_Id(Long id);

}
