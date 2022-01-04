package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.AdNotFoundException;
import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.Image;
import com.example.toolexchangeservice.repository.AdRepository;
import com.example.toolexchangeservice.service.image.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public List<AdDetail> getAllAds(){
        return this.adRepository.findAll();
    }

    public AdDetail getAdById(Long id) {
        return this.adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Oglas s id-om " + id + " nije pronađen"));
    }
}
