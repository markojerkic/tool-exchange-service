package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.constants.ExchangeOfferStatus;
import com.example.toolexchangeservice.model.dto.ExchangeOfferPreviewDto;
import com.example.toolexchangeservice.model.dto.OfferAcceptance;
import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import com.example.toolexchangeservice.service.ExchangeOfferService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("acceptance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ExchangeOffer acceptOrDeclineOffer(@RequestBody OfferAcceptance acceptance) {
        return this.exchangeOfferService.acceptOrDeclineOffer(acceptance);
    }

    @GetMapping
    public Page<ExchangeOfferPreviewDto> getOffers(@SortDefault(value = "id") Pageable pageable,
                                                   @RequestParam Optional<String> advertTitle,
                                                   @RequestParam Optional<String> from,
                                                   @RequestParam Optional<Long> suggestedTimeframe,
                                                   @RequestParam Optional<ExchangeOfferStatus> status) {
        return this.exchangeOfferService.getOffers(pageable, advertTitle, from, suggestedTimeframe, status);
    }
}
