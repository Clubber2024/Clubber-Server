package com.clubber.ClubberServer.domain.club.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubByDivisionResponse { //각 분과별 dto
    private String division;
    private List<GetClubIntoCardResponse> clubs;


    public static GetClubByDivisionResponse of (String division, List<GetClubIntoCardResponse> clubs){
        return GetClubByDivisionResponse.builder()
                .division(division)
                .clubs(clubs)
                .build();
    }

}
