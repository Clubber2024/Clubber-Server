package com.clubber.ClubberServer.domain.recruit.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRecruitRequest {
    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<String> imageKey;
}
