package com.example.toolexchangeservice.service;

import com.example.toolexchangeservice.model.dto.UserRatingDto;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.model.entity.UserRating;
import com.example.toolexchangeservice.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserManagementService userManagementService;
    private final AuthService authService;

    public void addNewRating(UserRatingDto userRating) {
        UserDetail aboutUser = this.userManagementService.loadUserByUsername(userRating.getAboutUser());
        Float averageRating;
        if (Objects.isNull(aboutUser.getAverageRating())) {
            averageRating = Float.valueOf(userRating.getMark());
        } else {
         averageRating = (aboutUser.getAverageRating() + userRating.getMark()) / 2;
        }
        this.userManagementService.updateAverageRating(aboutUser, averageRating);

        UserRating rating = new UserRating();

        UserDetail fromUser = this.authService.getLoggedInUser();
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
        rating.setAboutUser(userRating.getAboutUser().getUsername());
        rating.setFromUser(userRating.getFromUser().getUsername());
        rating.setLastModified(userRating.getLastModified());
        rating.setMark(userRating.getMark());

        return rating;
    }
}
