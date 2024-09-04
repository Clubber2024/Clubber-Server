package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.ClubType;
import lombok.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsSearchResponse {

    Map<String, List<GetClubSearchResponse>> clubs;

    public static GetClubsSearchResponse of (Map<String, List<GetClubSearchResponse>> groupedClubs){
        return GetClubsSearchResponse.builder()
                .clubs(groupedClubs)
                .build();
    }

}