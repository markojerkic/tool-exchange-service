package com.example.toolexchangeservice.repository;

import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Long> {
}
