package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.dto.ExchangeOfferPreviewDto;
import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import com.example.toolexchangeservice.service.ExchangeOfferService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/offer")
@AllArgsConstructor
@CrossOrigin("*")
public class ExchangeOfferController {
    private final ExchangeOfferService exchangeOfferService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ExchangeOffer addNewOffer(@RequestBody ExchangeOffer offer) {
        return this.exchangeOfferService.addNewOffer(offer);
    }

    @GetMapping
    public Page<ExchangeOfferPreviewDto> getOffers(@SortDefault(value = "id") Pageable pageable) {
        return this.exchangeOfferService.getOffers(pageable);
    }
}
