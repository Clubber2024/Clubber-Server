package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Division;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubByDivisionResponse { //각 분과별 dto

    private String division;
    private List<GetClubIntoCardResponse> clubs;

    public static GetClubByDivisionResponse of (Division division, List<GetClubIntoCardResponse> clubs){
        return GetClubByDivisionResponse.builder()
                .division(division.getTitle())
                .clubs(clubs)
                .build();
    }

}
