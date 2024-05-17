package com.clubber.ClubberServer.domain.user.controller;


import com.clubber.ClubberServer.domain.review.dto.UserReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.UserReviewResponse.ReviewResponse;
import com.clubber.ClubberServer.domain.user.dto.UserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.UserFavoritesResponse.FavoriteResponse;
import com.clubber.ClubberServer.domain.user.dto.UserProfileResponse;
import com.clubber.ClubberServer.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 정보 조회")
    @GetMapping("/me")
    public UserProfileResponse getUserProfile(){
        return userService.getUserProfile();
    }

    @Operation(summary = "회원 즐겨찾기 조회")
    @GetMapping("/favorite")
    public UserFavoritesResponse getUserFavorites() {
        return userService.getUserFavorites();
    }

    @Operation(summary = "회원 작성 리뷰 조회")
    @GetMapping("/review")
    public UserReviewResponse getUserReviews() { return userService.getUserReviews(); }
}
