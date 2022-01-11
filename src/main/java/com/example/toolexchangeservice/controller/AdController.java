package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.dto.AdvertPreviewDTO;
import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.model.entity.ToolState;
import com.example.toolexchangeservice.service.AdService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api/advert")
@AllArgsConstructor
@CrossOrigin("*")
public class AdController {
    private AdService adService;

    @PostMapping
    public AdDetail createNewAd(@RequestBody @Validated AdDetail ad){
        return this.adService.saveAd(ad);
    }

    @GetMapping
    public Page<AdvertPreviewDTO> getAllAds(@SortDefault(value = "lastModified",
            direction = Sort.Direction.DESC) Pageable pageable,
                                            @RequestParam Optional<String> title,
                                            @RequestParam Optional<Double> maxRange,
                                            @RequestParam Optional<Integer> minPower,
                                            @RequestParam Optional<Integer> maxPower,
                                            @RequestParam Optional<Boolean> electric,
                                            @RequestParam Optional<Boolean> nonelectric,
                                            @RequestParam Optional<Boolean> hasBattery,
                                            @RequestParam Optional<ToolState> condition){
        return this.adService.getPagedAdverts(pageable, title, maxRange, minPower, maxPower, electric,
                nonelectric, hasBattery, condition);
    }

    @DeleteMapping("/delete/id={id}")
    public void deleteAd(@PathVariable Long id){
        this.adService.deleteAdById(id);
    }

    @GetMapping("{id}")
    public AdDetail getAdById(@PathVariable Long id) {
        return this.adService.getAdById(id);
    }
}
