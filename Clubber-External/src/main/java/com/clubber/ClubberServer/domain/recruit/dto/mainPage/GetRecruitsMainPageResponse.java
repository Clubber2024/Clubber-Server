package com.clubber.ClubberServer.domain.recruit.dto.mainPage;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetRecruitsMainPageResponse {

    private final List<GetOneRecruitMainPageResponse> recruits;

    public static GetRecruitsMainPageResponse from(List<GetOneRecruitMainPageResponse> recruits) {
        return GetRecruitsMainPageResponse.builder()
            .recruits(recruits)
            .build();
    }

}
