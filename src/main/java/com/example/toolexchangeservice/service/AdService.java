package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.AdNotFoundException;
import com.example.toolexchangeservice.model.dto.AdvertPreviewDTO;
import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.model.entity.ToolState;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.repository.AdRepository;
import com.example.toolexchangeservice.service.image.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
@AllArgsConstructor
public class AdService {
    private final AdRepository adRepository;
    private final AuthService authService;
    private final ImageService imageService;

    public AdDetail saveAd(AdDetail adDetail){
        adDetail.setCreator(this.authService.getLoggedInUser());
        adDetail.setLastModified(new Date());

        AdDetail savedAdvert = this.adRepository.save(adDetail);
        adDetail.getImages().stream().map(image -> this.addAdvertInfoToImage(image, savedAdvert))
                .forEach(this.imageService::saveImage);

        return savedAdvert;
    }

    private Image addAdvertInfoToImage(Image image, AdDetail advert) {
        image.setAdvert(advert);
        return image;
    }

    public AdDetail getAdById(Long id) {
        return this.adRepository.findById(id).orElseThrow(() -> new
                AdNotFoundException("Oglas s id-om " + id + " nije pronađen"));
    }

    public Page<AdvertPreviewDTO> getPagedAdverts(Pageable pageable, Optional<String> title, Optional<Double> maxRange,
                                                  Optional<Integer> minPower, Optional<Integer> maxPower,
                                                  Optional<Boolean> electric, Optional<Boolean> nonelectric,
                                                  Optional<Boolean> hasBattery, Optional<ToolState> condition) {
        Page<AdDetail> advertPage = this.adRepository.findAll(this.createQuerySpecification(title, maxRange, minPower,
                maxPower, electric, nonelectric, hasBattery, condition), pageable);
        return advertPage.map(this::mapToAdvertPreview);
    }

    private Specification<AdDetail> createQuerySpecification(Optional<String> title, Optional<Double> maxRange,
                                                             Optional<Integer> minPower, Optional<Integer> maxPower,
                                                             Optional<Boolean> electric, Optional<Boolean> nonelectric,
                                                             Optional<Boolean> hasBattery, Optional<ToolState> condition) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            title.ifPresent(tod -> predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder
                                    .upper(root.get("title")),
                            "%" + tod.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder
                                    .upper(root.get("details")),
                            "%" + tod.toUpperCase() + "%"))
            ));

            minPower.ifPresent(power -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("tool").get("power"), power
            )));

            maxPower.ifPresent(power -> predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("tool").get("power"), power
            )));

            electric.ifPresent(e -> predicates.add(criteriaBuilder.equal(
                    root.get("tool").get("isElectric"), e
            )));

            nonelectric.ifPresent(e -> predicates.add(criteriaBuilder.equal(
                    root.get("tool").get("isElectric"), !e
            )));

            hasBattery.ifPresent(hb -> predicates.add(criteriaBuilder.equal(
                    root.get("tool").get("hasBattery"), hb
            )));

            condition.ifPresent(cd -> predicates.add(criteriaBuilder.equal(
                    root.get("tool").get("toolState"), cd
            )));

            maxRange.ifPresent(range -> {
                UserDetail user = this.authService.getLoggedInUser();
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                criteriaBuilder.function("calculate_distance", Double.class,
                                        root.get("creator").get("lat"),
                                        root.get("creator").get("lng"),
                                        criteriaBuilder.toDouble(criteriaBuilder.literal(user.getLat())),
                                        criteriaBuilder.toDouble(criteriaBuilder.literal(user.getLng()))),
                                range
                        )
                );
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public AdvertPreviewDTO mapToAdvertPreview(AdDetail adDetail) {
        AdvertPreviewDTO previewDTO = new AdvertPreviewDTO();
        previewDTO.setId(adDetail.getId());
        previewDTO.setTitle(adDetail.getTitle());
        previewDTO.setDetails(adDetail.getDetails());
        previewDTO.setUserCreated(adDetail.getCreator().getUsername());
        previewDTO.setThumbnailImageUuid(this.getThumbnailImage(adDetail));

        return previewDTO;
    }

    @Secured("ROLE_ADMIN")
    public void deleteAdById(Long id){
        adRepository.deleteById(id);
    }

    private UUID getThumbnailImage(AdDetail adDetail) {
        return this.imageService.getImagesByAdvertId(adDetail.getId()).stream().map(Image::getUuid)
                .findFirst().orElse(null);
    }
}
