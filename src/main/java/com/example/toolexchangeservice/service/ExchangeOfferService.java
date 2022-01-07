package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.OfferNotFound;
import com.example.toolexchangeservice.model.constants.ExchangeOfferStatus;
import com.example.toolexchangeservice.model.dto.ExchangeOfferPreviewDto;
import com.example.toolexchangeservice.model.dto.OfferAcceptance;
import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.ExchangeOffer;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.ExchangeOfferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        this.mailService.sendOfferMail(adDetail.getCreator().getEmail(), adDetail.getTitle(), fromUser.getEmail(),
                offer.getSuggestedTimeframe(), offer.getMessage());

        return this.exchangeOfferRepository.save(offer);
    }

    public Page<ExchangeOfferPreviewDto> getOffers(Pageable pageable, Optional<String> advertTitle, Optional<String> from,
                                                   Optional<Long> suggestedTimeframe, Optional<ExchangeOfferStatus> status) {
        return this.exchangeOfferRepository.findAll(this.createQuerySpecification(advertTitle, from,
                suggestedTimeframe, status, this.authService.getLoggedInUser().getId()),
                pageable).map(this::mapToPreview);
    }

    private Specification<ExchangeOffer> createQuerySpecification(Optional<String> advertTitle, Optional<String> from,
                                                                  Optional<Long> suggestedTimeframe,
                                                                  Optional<ExchangeOfferStatus> status, Long userId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            advertTitle.ifPresent(title -> predicates.add(criteriaBuilder.like(criteriaBuilder
                                    .upper(root.get("advert").get("title")),
                            "%" + title.toUpperCase() + "%")));
            from.ifPresent(username -> predicates.add(criteriaBuilder.like(criteriaBuilder
                                    .upper(root.get("offerFrom").get("username")),
                            "%" + username.toUpperCase() + "%")));
            suggestedTimeframe.ifPresent(date -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("suggestedTimeframe"),
                    new Date(date))));
            status.ifPresent(offerStatus -> predicates.add(criteriaBuilder.equal(root.get("offerStatus"),
                    offerStatus)));

            predicates.add(criteriaBuilder.equal(root.get("advert").get("creator")
                            .get("id"),
                    userId));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
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

    public ExchangeOffer acceptOrDeclineOffer(OfferAcceptance acceptance) {
        ExchangeOffer offer = this.exchangeOfferRepository.findById(acceptance.getId()).orElseThrow(() ->
                new OfferNotFound("Offer " + acceptance.getId() + " not found"));

        AdDetail adDetail = this.adService.getAdById(offer.getAdvert().getId());
        UserDetail fromUser = this.authService.getLoggedInUser();

        switch (acceptance.getType()) {
            case ACCEPT:
                offer.setOfferStatus(ExchangeOfferStatus.ACCEPTED);
                this.mailService.sendAcceptanceMail(fromUser.getEmail(), adDetail.getTitle());
                break;
            case REJECT:
                offer.setOfferStatus(ExchangeOfferStatus.REJECTED);
                this.mailService.sendRejectionMail(fromUser.getEmail(), adDetail.getTitle());
                break;
            default:
                throw new RuntimeException(acceptance.getType().name());
        }

        return this.exchangeOfferRepository.save(offer);
    }
}
