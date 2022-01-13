package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.dto.RatingSum;
import com.example.toolexchangeservice.model.dto.UserRatingDto;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.model.entity.UserRating;
import com.example.toolexchangeservice.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserManagementService userManagementService;
    private final AuthService authService;
    private final MailService mailService;

    public void addNewRating(UserRatingDto userRating) {
        UserDetail aboutUser = this.userManagementService.loadUserByUsername(userRating.getAboutUser());
        UserDetail fromUser = this.authService.getLoggedInUser();

        if (aboutUser.equals(fromUser)) {
            throw new AccessDeniedException("Ne možete ocijeniti sami sebe");
        }

        Float averageRating;
        if (Objects.isNull(aboutUser.getAverageRating())) {
            averageRating = Float.valueOf(userRating.getMark());
        } else {
         averageRating = (aboutUser.getAverageRating() + userRating.getMark()) / 2;
        }
        this.userManagementService.updateAverageRating(aboutUser, averageRating);

        UserRating rating = new UserRating();
        rating.setFromUser(fromUser);
        rating.setAboutUser(aboutUser);
        rating.setLastModified(new Date());
        rating.setMark(userRating.getMark());

        this.ratingRepository.save(rating);
    }

    public List<UserRatingDto> getLastFiveRatingsAboutUser(String username) {
        return this.ratingRepository.findAllByAboutUser_UsernameOrderByLastModifiedDesc(username, Pageable.ofSize(5))
                .stream().map(this::mapToRatingDto).collect(Collectors.toList());
    }

    private UserRatingDto mapToRatingDto(UserRating userRating) {
        UserRatingDto rating = new UserRatingDto();
        rating.setId(userRating.getId());
        rating.setAboutUser(userRating.getAboutUser().getUsername());
        rating.setFromUser(userRating.getFromUser().getUsername());
        rating.setLastModified(userRating.getLastModified());
        rating.setMark(userRating.getMark());

        return rating;
    }

    @Async
    public void findBestHandymanUntilToday() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.withDayOfMonth(1);
        this.setBestHandyman(lastMonth, today);
    }

    private void setBestHandyman(LocalDate from, LocalDate to) {
        Map<UserDetail, List<UserRating>> userRatings = this.ratingRepository
                .findAllByLastModifiedBetween(java.sql.Date.valueOf(from),
                        java.sql.Date.valueOf(to)).stream().collect(Collectors.groupingBy(UserRating::getAboutUser));

        RatingSum bestHandyman = userRatings.entrySet().stream().map(this::mapToRatingSum)
                .max(Comparator.comparing(RatingSum::getSum)).orElseThrow();

        this.userManagementService.setNewBestHandyMan(bestHandyman.getUser());
    }

    @Scheduled(cron = "0 0 0 1 * *")
    @Async
    public void findBestHandyman() {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);
        this.setBestHandyman(lastMonth, today);
    }

    public RatingSum mapToRatingSum(Map.Entry<UserDetail, List<UserRating>> userRatings) {
        RatingSum sum = new RatingSum();
        Integer ratingsSum = userRatings.getValue().stream().map(UserRating::getMark)
                .reduce(0, Integer::sum);

        sum.setSum(ratingsSum);
        sum.setUser(userRatings.getKey());
        return sum;
    }
}
