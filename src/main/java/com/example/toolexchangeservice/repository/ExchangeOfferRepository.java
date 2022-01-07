package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Long>,
        PagingAndSortingRepository<ExchangeOffer, Long> {
    Page<ExchangeOffer> findAllByAdvert_Creator_Id(Pageable pageable, Long id);
}
