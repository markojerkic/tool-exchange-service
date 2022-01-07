package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.ExchangeOfferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ExchangeOfferService {
    private final ExchangeOfferRepository exchangeOfferRepository;
    private final MailService mailService;
    private final AdService adService;
    private final AuthService authService;

    public ExchangeOffer addNewOffer(ExchangeOffer offer) {
        AdDetail adDetail = this.adService.getAdById(offer.getId());
        UserDetail fromUser = this.authService.getLoggedInUser();

        offer.setOfferFrom(fromUser);

        this.mailService.sendMail(adDetail.getCreator().getEmail(), adDetail.getTitle(), fromUser.getEmail(),
                offer.getSuggestedTimeframe(), offer.getMessage());

        return this.exchangeOfferRepository.save(offer);
    }
}
