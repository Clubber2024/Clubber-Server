package com.clubber.ClubberServer.domain.recruit.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRecruitRequest {
    private String title;
    private String content;
    private List<String> imageUrl;
}
