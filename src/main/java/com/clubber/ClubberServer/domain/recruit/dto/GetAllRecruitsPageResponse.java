package com.clubber.ClubberServer.domain.recruit.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;



@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAllRecruitsPageResponse {

    private List<GetRecruitPageResponse> recruits;

    public static GetAllRecruitsPageResponse from(List<GetRecruitPageResponse> recruits){
        return GetAllRecruitsPageResponse.builder()
                .recruits(recruits)
                .build();
    }

}