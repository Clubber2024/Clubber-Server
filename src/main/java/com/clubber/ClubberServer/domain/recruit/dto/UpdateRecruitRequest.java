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

    private List<String> deletedImageUrls; //삭제

    private List<String> newImageKeys; //추가

    private List<String> remainImageUrls; //유지

    private List<String> images; //최종(추가+유지) - 저장할 순서대로 담겨져 있음

    private List<Long> order;

}
