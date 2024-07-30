package com.clubber.ClubberServer.domain.recruit.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetRecruitsByClubIdResponse {
    private Long clubId;

    private List<GetRecruitResponse> recruits;

    public static GetRecruitsByClubIdResponse of(Long clubId,List<GetRecruitResponse> recruits){
        return GetRecruitsByClubIdResponse.builder()
                .clubId(clubId)
                .recruits(recruits)
                .build();
    }

}
