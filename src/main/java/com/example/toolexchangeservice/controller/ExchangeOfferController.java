package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import com.example.toolexchangeservice.service.ExchangeOfferService;
import lombok.AllArgsConstructor;
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
}
