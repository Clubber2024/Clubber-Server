package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.ClubType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOfficialClubGroupResponse {


    @Schema(description = "동아리 종류", example = "숭실대 공식 단체")
    private final String clubType;

    @Schema(description = "숭실대 공식 단체 목록")
    private final List<GetOfficialClubResponse> clubs;

    public static GetOfficialClubGroupResponse of(ClubType clubType,
        List<GetOfficialClubResponse> clubs) {
        return GetOfficialClubGroupResponse.builder()
            .clubType(clubType.getTitle())
            .clubs(clubs)
            .build();
    }
}

