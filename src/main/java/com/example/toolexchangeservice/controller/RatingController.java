package com.example.toolexchangeservice.controller;


import com.example.toolexchangeservice.model.dto.UserRatingDto;
import com.example.toolexchangeservice.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rating")
@AllArgsConstructor
@CrossOrigin("*")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("{username}")
    public List<UserRatingDto> getLastFiveRatingsByUsername(@PathVariable String username) {
        return this.ratingService.getLastFiveRatingsAboutUser(username);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void addUserRating(@RequestBody UserRatingDto rating) {
        this.ratingService.addNewRating(rating);
    }
}
