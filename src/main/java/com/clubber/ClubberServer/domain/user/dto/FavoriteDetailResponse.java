package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteDetailResponse {

    @Schema(description = "즐겨찾기 id", example = "1")
    private final Long favoriteId;

    @Schema(description = "즐겨찾기한 동아리")
    private final FavoriteClubDetailResponse favoriteClub;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    private static class FavoriteClubDetailResponse {

        @Schema(description = "동아리 id", example = "1")
        private final Long clubId;

        @Schema(description = "동아리 이름", example = "로타랙트")
        private final String clubName;

        @Schema(description = "동아리 종류", example = "중앙동아리")
        private final String clubType;

        @Schema(description = "동아리 이미지 url")
        private final ImageVO imageUrl;

        public static FavoriteClubDetailResponse of(Club club) {
            return FavoriteClubDetailResponse.builder()
                    .clubId(club.getId())
                    .clubName(club.getName())
                    .clubType(club.getClubType().getTitle())
                    .imageUrl(club.getImageUrl())
                    .build();
        }
    }

    public static FavoriteDetailResponse of(Favorite favorite) {
        FavoriteClubDetailResponse favoriteClubDetailResponse = FavoriteClubDetailResponse.of(favorite.getClub());
        return FavoriteDetailResponse
                .builder()
                .favoriteId(favorite.getId())
                .favoriteClub(favoriteClubDetailResponse).build();
    }
}
