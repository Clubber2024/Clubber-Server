package com.clubber.ClubberServer.domain.club.dto;

import lombok.*;

import java.util.List;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsSearchResponse {
    private String clubName;
    private List<GetClubSearchResponse> clubs;


    public static GetClubsSearchResponse of (String clubName, List<GetClubSearchResponse> clubs){
        return GetClubsSearchResponse.builder()
                .clubName(clubName)
                .clubs(clubs)
                .build();
    }

}