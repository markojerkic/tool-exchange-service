package com.example.toolexchangeservice.rating;

import com.example.toolexchangeservice.model.dto.UserRatingDto;
import com.example.toolexchangeservice.model.entity.UserDetail;
import com.example.toolexchangeservice.model.entity.UserRating;
import com.example.toolexchangeservice.repository.RatingRepository;
import com.example.toolexchangeservice.service.AuthService;
import com.example.toolexchangeservice.service.RatingService;
import com.example.toolexchangeservice.service.UserManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class RatingTest {

    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private UserManagementService userManagementService;
    @Mock
    private AuthService authService;

    @InjectMocks
    private RatingService ratingService;

    @Test
    public void testAddNewRating() {

        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName("Marko");
        userDetail.setLastName("Jerkić");
        userDetail.setUsername("marko");
        userDetail.setEmail("marko@marko.com");

        UserDetail userDetail2 = new UserDetail();
        userDetail2.setFirstName("Marko");
        userDetail2.setLastName("Jerkić");
        userDetail2.setUsername("marko2");
        userDetail2.setEmail("marko2@marko.com");

        UserRatingDto rating = new UserRatingDto();
        rating.setAboutUser(userDetail.getUsername());
        rating.setFromUser(userDetail2.getUsername());
        rating.setMark(5);

        Mockito.when(userManagementService.loadUserByUsername(userDetail.getUsername())).thenReturn(userDetail);

        Mockito.when(authService.getLoggedInUser()).thenReturn(userDetail2);

//        Mockito.when(Objects.isNull(Mockito.any())).thenReturn(true);
        Mockito.verify(userManagementService, Mockito.atMostOnce())
                .loadUserByUsername(userDetail.getUsername());

        ratingService.addNewRating(rating);
    }

    @Test
    public void testAddNewSelfRating() {

        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName("Marko");
        userDetail.setLastName("Jerkić");
        userDetail.setUsername("marko");
        userDetail.setEmail("marko@marko.com");

        UserRatingDto rating = new UserRatingDto();
        rating.setAboutUser(userDetail.getUsername());
        rating.setFromUser(userDetail.getUsername());
        rating.setMark(5);

        Mockito.when(userManagementService.loadUserByUsername(userDetail.getUsername())).thenReturn(userDetail);

        Mockito.when(authService.getLoggedInUser()).thenReturn(userDetail);

        assertThrows(AccessDeniedException.class, () -> ratingService.addNewRating(rating));
    }
}
