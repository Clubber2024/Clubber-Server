package com.clubber.ClubberServer.domain.recruit.dto;


import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOneRecruitResponse {
    private Long clubId;
    private Long recruitId;
    private String title;
    private String content;
    private String imageUrl;
    private Long totalView;
    private LocalDateTime createdAt;



    public static GetOneRecruitResponse from(Recruit recruit){
        return GetOneRecruitResponse.builder()
                .clubId(recruit.getClub().getId())
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .imageUrl(recruit.getImageUrl())
                .totalView(recruit.getTotalView())
                .createdAt(recruit.getCreatedAt())
                .build();
    }

}
