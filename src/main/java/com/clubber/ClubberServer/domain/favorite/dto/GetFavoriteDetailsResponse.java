package com.clubber.ClubberServer.domain.favorite.dto;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetFavoriteDetailsResponse {

	@Schema(description = "즐겨찾기 id", example = "1")
	private final Long favoriteId;

	@Schema(description = "동아리 id", example = "1")
	private final Long clubId;

	@Schema(description = "동아리 이름", example = "로타랙트")
	private final String clubName;

	@Schema(description = "동아리 종류", example = "중앙동아리")
	private final String clubType;

	@Schema(description = "동아리 이미지 url")
	private final String imageUrl;

	public static GetFavoriteDetailsResponse of(Favorite favorite) {
		return GetFavoriteDetailsResponse.builder()
			.favoriteId(favorite.getId())
			.clubId(favorite.getClub().getId())
			.clubName(favorite.getClub().getName())
			.clubType(favorite.getClub().getClubType().getTitle())
			.imageUrl(favorite.getClub().getImageUrl())
			.build();
	}
}
