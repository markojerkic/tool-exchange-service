package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.dto.AdvertPreviewDTO;
import com.example.toolexchangeservice.model.entity.AdDetail;
import com.example.toolexchangeservice.service.AdService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
            direction = Sort.Direction.DESC) Pageable pageable){
        return this.adService.getPagedAdverts(pageable);
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
