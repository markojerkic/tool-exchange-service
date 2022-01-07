package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Long>,
        PagingAndSortingRepository<ExchangeOffer, Long>, JpaSpecificationExecutor<ExchangeOffer> {
    @Query("select count(o) from ExchangeOffer o where o.advert.creator.id = :id and o.offerStatus = 'PENDING'")
    Integer countPendingOffers(@Param("id") Long userId);
}
