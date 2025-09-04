package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsSearchForSignUpResponse {

    @Schema(description = "동아리 id")
    private Long clubId;

    @Schema(description = "동아리명", example = "클러버")
    private final String clubName;

    @Schema(description = "동아리 type", example = "CENTER")
    private final String clubType;

    public static GetClubsSearchForSignUpResponse from(Club club) {
        return GetClubsSearchForSignUpResponse.builder()
                .clubId(club.getId())
                .clubName(club.getName())
                .clubType(club.getClubType().getCode())
                .build();
    }
}
