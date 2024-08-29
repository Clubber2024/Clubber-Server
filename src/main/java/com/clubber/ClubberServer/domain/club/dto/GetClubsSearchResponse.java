package com.clubber.ClubberServer.domain.club.dto;

import lombok.*;

import java.util.List;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsSearchResponse {
    private String clubName;

    private List<GetClubSearchResponse> center;
    private List<GetClubSearchResponse> small;


    public static GetClubsSearchResponse of (String clubName, List<GetClubSearchResponse> center,List<GetClubSearchResponse> small){
        return GetClubsSearchResponse.builder()
                .clubName(clubName)
                .center(center)
                .small(small)
                .build();
    }

}