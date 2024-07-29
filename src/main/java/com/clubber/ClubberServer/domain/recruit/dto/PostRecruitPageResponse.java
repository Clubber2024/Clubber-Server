package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRecruitPageResponse {
    private Long recruitId;
    private String title;
    private String content;
    private String imageUrl;
    private Long totalView;

    public static PostRecruitPageResponse of(Recruit recruit){
        return PostRecruitPageResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .imageUrl(recruit.getImageUrl())
                .totalView(recruit.getTotalView())
                .build();
    }

}
