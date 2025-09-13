package com.clubber.domain.club.dto;

import com.clubber.domain.domains.club.domain.ClubType;
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
    private final String clubType = ClubType.OFFICIAL.getTitle();

    @Schema(description = "숭실대 공식 단체 목록")
    private final List<GetOfficialClubResponse> clubs;

    public static GetOfficialClubGroupResponse from(List<GetOfficialClubResponse> clubs) {
        return GetOfficialClubGroupResponse.builder()
                .clubs(clubs)
                .build();
    }
}

