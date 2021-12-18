package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.service.AdService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/ad")
@AllArgsConstructor
public class AdController {
    private AdService adService;

    @PostMapping
    public AdDetail createNewAd(@RequestBody AdDetail ad){
        return this.adService.saveAd(ad);
    }

    @GetMapping
    public List<AdDetail> getAllAds(){
        return this.adService.getAllAds();
    }
}
