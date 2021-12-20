package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.config.exception.AdNotFoundException;
import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.repository.AdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdService {
    private final AdRepository adRepository;

    public AdDetail saveAd(AdDetail adDetail){
        return this.adRepository.save(adDetail);
    }

    public List<AdDetail> getAllAds(){
        return this.adRepository.findAll();
    }

    public AdDetail getAdById(Long id) {
        return this.adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Oglas s id-om " + id + " nije pronađen"));
    }
}
