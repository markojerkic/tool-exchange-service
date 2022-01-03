package com.example.toolexchangeservice.controller;

import com.example.toolexchangeservice.model.location.LocationSearchResult;
import com.example.toolexchangeservice.service.LocationSearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/location/search")
@CrossOrigin("*")
@AllArgsConstructor
public class LocationSearchController {
    private final LocationSearchService locationSearchService;

    @GetMapping
    public LocationSearchResult searchLocations(@RequestParam String query) {
        return this.locationSearchService.searchLocations(query);
    }
}
