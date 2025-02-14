package com.clubber.ClubberServer.domain.user.controller;


import com.clubber.ClubberServer.domain.favorite.dto.GetFavoriteDetailsResponse;
import com.clubber.ClubberServer.domain.user.dto.GetIsUserFavoriteResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserProfileResponse;
import com.clubber.ClubberServer.domain.user.service.UserService;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "[회원]")
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원 정보 조회")
	@GetMapping("/me")
	public GetUserProfileResponse getUserProfile() {
		return userService.getUserProfile();
	}

	@Operation(summary = "회원 즐겨찾기 조회")
	@GetMapping("/favorite")
	public GetUserFavoritesResponse getUserFavorites() {
		return userService.getUserFavorites();
	}

	@Operation(summary = "내가 쓴 리뷰 조회")
	@GetMapping("/review")
	public GetUserReviewsResponse getUserReviews() {
		return userService.getUserReviews();
	}

	@Operation(summary = "즐겨찾기 페이징 처리", description = "추후 적용")
	@GetMapping("/favorite/page")
	public PageResponse<GetFavoriteDetailsResponse> getUsersFavoritePage(
		@ParameterObject Pageable pageable) {
		return userService.getUserFavoritesPagination(pageable);
	}

	@Operation(summary = "회원 동아리 즐겨찾기 여부", description = "개별 동아리 페이지 즐겨찾기 (별표) 판단")
	@GetMapping("/favorite/{clubId}")
	public GetIsUserFavoriteResponse getIsUserFavorite(@PathVariable Long clubId) {
		return userService.getIsUserFavorite(clubId);
	}
}
