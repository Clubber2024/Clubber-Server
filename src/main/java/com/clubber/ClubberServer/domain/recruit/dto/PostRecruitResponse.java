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
public class PostRecruitResponse {
    private Long recruitId;
    private String title;
    private String content;
    private List<String> images;
    private Long totalView;

    public static PostRecruitResponse of(Recruit recruit, List<String> images){
        return PostRecruitResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .images(images)
                .totalView(recruit.getTotalView())
                .build();
    }

}
