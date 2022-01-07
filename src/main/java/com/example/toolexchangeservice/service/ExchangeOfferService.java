package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.constants.ExchangeOfferStatus;
import com.example.toolexchangeservice.model.dto.ExchangeOfferPreviewDto;
import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.ExchangeOfferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        AdDetail adDetail = this.adService.getAdById(offer.getAdvert().getId());
        UserDetail fromUser = this.authService.getLoggedInUser();

        offer.setOfferFrom(fromUser);
        offer.setOfferStatus(ExchangeOfferStatus.PENDING);

        this.mailService.sendMail(adDetail.getCreator().getEmail(), adDetail.getTitle(), fromUser.getEmail(),
                offer.getSuggestedTimeframe(), offer.getMessage());

        return this.exchangeOfferRepository.save(offer);
    }

    public Page<ExchangeOfferPreviewDto> getOffers(Pageable pageable) {
        return this.exchangeOfferRepository.findAllByAdvert_Creator_Id(pageable,
                this.authService.getLoggedInUser().getId()).map(this::mapToPreview);
    }

    private ExchangeOfferPreviewDto mapToPreview(ExchangeOffer offer) {
        ExchangeOfferPreviewDto preview = new ExchangeOfferPreviewDto();
        preview.setId(offer.getId());
        preview.setFrom(offer.getOfferFrom().getUsername());
        preview.setStatus(offer.getOfferStatus());
        preview.setAdvertTitle(offer.getAdvert().getTitle());
        preview.setSuggestedTimeframe(offer.getSuggestedTimeframe());

        return preview;
    }
}
