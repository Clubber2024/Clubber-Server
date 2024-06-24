package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFavoritesResponse {

    @Schema(description = "유저 id", example = "1")
    private final Long userId;

    private final List<FavoriteDetailResponse> userFavorites;


    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FavoriteDetailResponse {

        @Schema(description = "즐겨찾기 id", example = "1")
        private final Long favoriteId;

        @Schema(description = "즐겨찾기한 동아리")
        private final FavoriteClubDetailResponse favoriteClub;

        @Getter
        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        @Builder(access = AccessLevel.PRIVATE)
        public static class FavoriteClubDetailResponse {

            @Schema(description = "동아리 id", example = "1")
            private final Long clubId;

            @Schema(description = "동아리 이름", example = "로타랙트")
            private final String clubName;

            @Schema(description = "동아리 id", example = "center")
            private final String clubType;

            private static FavoriteClubDetailResponse of(Club club){
                return FavoriteClubDetailResponse.builder()
                        .clubId(club.getId())
                        .clubName(club.getName())
                        .clubType(club.getClubType()).build();
            }
        }

        private static FavoriteDetailResponse of(Favorite favorite){
            FavoriteClubDetailResponse favoriteClubDetailResponse = FavoriteClubDetailResponse.of(favorite.getClub());
            return FavoriteDetailResponse
                    .builder()
                    .favoriteId(favorite.getId())
                    .favoriteClub(favoriteClubDetailResponse).build();
        }
    }

    public static UserFavoritesResponse of (User user, List<Favorite> favorites){
        List<FavoriteDetailResponse> favoriteDetailResponse = favorites.stream()
                .map(FavoriteDetailResponse::of).collect(Collectors.toList());

        return UserFavoritesResponse.builder()
                .userId(user.getId())
                .userFavorites(favoriteDetailResponse).build();

    }
}
