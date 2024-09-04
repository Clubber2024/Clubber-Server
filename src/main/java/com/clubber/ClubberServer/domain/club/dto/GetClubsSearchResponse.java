package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.ClubType;
import lombok.*;

import java.util.EnumMap;
import java.util.List;



@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsSearchResponse {

    EnumMap<ClubType, List<GetClubSearchResponse>> clubs;

    public static GetClubsSearchResponse of (EnumMap<ClubType, List<GetClubSearchResponse>> groupedClubs){
        return GetClubsSearchResponse.builder()
                .clubs(groupedClubs)
                .build();
    }

}