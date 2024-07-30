package com.clubber.ClubberServer.domain.recruit.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRecruitRequest {
    private String title;
    private String content;
    private String imageUrl;
}
