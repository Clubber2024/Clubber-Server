package com.clubber.ClubberServer.domain.recruit.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;



@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAllRecruitsResponse {

    private List<GetOneRecruitResponse> recruits;

    public static GetAllRecruitsResponse from(List<GetOneRecruitResponse> recruits){
        return GetAllRecruitsResponse.builder()
                .recruits(recruits)
                .build();
    }

}