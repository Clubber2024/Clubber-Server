package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.global.vo.ImageVO;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRecruitRequest {
    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<String> deletedImageUrls;

    private List<String> newImageKeys;

    private List<String> remainImageUrls;

    private List<String> images;


}
