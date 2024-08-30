package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.ClubType;
import lombok.*;

import java.util.List;
import java.util.Map;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsSearchResponse {

    Map<ClubType, List<GetClubSearchResponse>> clubs;


    public static GetClubsSearchResponse of (Map<ClubType, List<GetClubSearchResponse>> groupedClubs){
        return GetClubsSearchResponse.builder()
                .clubs(groupedClubs)
                .build();
    }

}