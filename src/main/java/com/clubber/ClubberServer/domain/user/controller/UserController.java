package com.clubber.ClubberServer.domain.user.controller;


import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.FavoriteResponse;
import com.clubber.ClubberServer.domain.favorite.dto.GetFavoriteDetailsResponse;
import com.clubber.ClubberServer.domain.review.dto.UserReviewResponse;
import com.clubber.ClubberServer.domain.user.dto.UserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.UserProfileResponse;
import com.clubber.ClubberServer.domain.user.service.UserService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "[회원]")
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

    //version1
//    @Operation(summary = "회원 작성 리뷰 조회")
//    @GetMapping("/review")
//    public UserReviewResponse getUserReviews() { return userService.getUserReviews(); }

    @Operation(summary = "내가 쓴 리뷰 조회")
    @GetMapping("/review")
    public UserReviewResponse getUserReviews(){
        return userService.getUserReviews();

    }

    @Operation(summary = "즐겨찾기 페이징 처리", description = "추후 적용")
    @GetMapping("/favorite/page")
    public PageResponse<GetFavoriteDetailsResponse> getUsersFavoritePage(@ParameterObject Pageable pageable) { return userService.getUsersReviewsPagination(pageable);}
}
