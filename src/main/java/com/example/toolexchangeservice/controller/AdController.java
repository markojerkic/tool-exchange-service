package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.service.AdService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ad")
@AllArgsConstructor
@CrossOrigin("*")
public class AdController {
    private AdService adService;

    @PostMapping
    @Validated
    public AdDetail createNewAd(@RequestBody AdDetail ad){
        return this.adService.saveAd(ad);
    }

    @GetMapping
    public List<AdDetail> getAllAds(){
        return this.adService.getAllAds();
    }

    @GetMapping("{id}")
    public AdDetail getAdById(@PathVariable Long id) {
        return this.adService.getAdById(id);
    }
}
