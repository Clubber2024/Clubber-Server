package com.clubber.ClubberServer.domain.recruit.dto.mainPage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetRecruitsMainPageResponse {

    private List<GetOneRecruitMainPageResponse> recruits;

    public static GetRecruitsMainPageResponse from(List<GetOneRecruitMainPageResponse> recruits){
        return GetRecruitsMainPageResponse.builder()
                .recruits(recruits)
                .build();
    }

}
