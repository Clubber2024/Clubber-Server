package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetRecruitsPageResponse {
    private Long clubId;

    private List<GetRecruitPageResponse> recruits;

    public static GetRecruitsPageResponse of(Long clubId,List<GetRecruitPageResponse> recruits){
        return GetRecruitsPageResponse.builder()
                .clubId(clubId)
                .recruits(recruits)
                .build();
    }

}
