package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.AdNotFoundException;
import com.example.toolexchangeservice.model.dto.AdvertPreviewDTO;
import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.repository.AdRepository;
import com.example.toolexchangeservice.service.image.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

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

    public Page<AdvertPreviewDTO> getPagedAdverts(Pageable pageable) {
        Page<AdDetail> advertPage = this.adRepository.findAll(pageable);
        return advertPage.map(this::mapToAdvertPreview);
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

    private UUID getThumbnailImage(AdDetail adDetail) {
        return this.imageService.getImagesByAdvertId(adDetail.getId()).stream().map(Image::getUuid)
                .findFirst().orElse(null);
    }
}
