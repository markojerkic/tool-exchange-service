package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.location.LocationSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class LocationSearchService {
    private final WebClient mapsApiWebClient;
    @Value("${maps.api.key}")
    private String mapsApikey;

    public LocationSearchService(WebClient mapsApiWebClient) {
        this.mapsApiWebClient = mapsApiWebClient;
    }

    public LocationSearchResult searchLocations(String query) {
        LocationSearchResult searchResult = this.mapsApiWebClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("key", this.mapsApikey)
                        .queryParam("language", "hr")
                        .queryParam("query", query)
                        .build())
                .retrieve().bodyToMono(LocationSearchResult.class).block();

        System.out.println(searchResult);
        return searchResult;
    }
}
