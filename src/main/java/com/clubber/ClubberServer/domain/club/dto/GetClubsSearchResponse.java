package com.clubber.ClubberServer.domain.club.dto;

import lombok.*;

import java.util.List;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsSearchResponse {

    private String searchBy;
    private List<GetClubSearchResponse> center;
    private List<GetClubSearchResponse> small;


    public static GetClubsSearchResponse of (String searchBy, List<GetClubSearchResponse> center,List<GetClubSearchResponse> small){
        return GetClubsSearchResponse.builder()
                .searchBy(searchBy)
                .center(center)
                .small(small)
                .build();
    }

}