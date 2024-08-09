package com.clubber.ClubberServer.domain.recruit.dto;


import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOneRecruitResponse {
    private Long clubId;
    private Long recruitId;
    private String title;
    private String content;
    private List<String> images;
    private Long totalView;
    private LocalDateTime createdAt;



    public static GetOneRecruitResponse of(Recruit recruit,List<String> images){
        return GetOneRecruitResponse.builder()
                .clubId(recruit.getClub().getId())
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .images(images)
                .totalView(recruit.getTotalView())
                .createdAt(recruit.getCreatedAt())
                .build();
    }

}