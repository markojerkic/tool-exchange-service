package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.location.LocationSearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class LocationService {

    private final String mapsApiEndpoint;
    private final String mapsApikey;

    private WebClient webClient;

    public LocationService(@Value("${maps.api.endpoint}") String mapsApiEndpoint,
                           @Value("${maps.api.key}") String mapsApikey) {
        this.mapsApiEndpoint = mapsApiEndpoint;
        this.mapsApikey = mapsApikey;
    }

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
            .baseUrl(this.mapsApiEndpoint)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public void searchLocations(String query) {
        LocationSearchResult searchResult = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("key", this.mapsApikey)
                .queryParam("language", "hr")
                .queryParam("query", query)
                .build())
                .retrieve().bodyToMono(LocationSearchResult.class).block();

        System.out.println(searchResult);
    }
}
