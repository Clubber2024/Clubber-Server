package com.clubber.ClubberServer.domain.recruit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRecruitRequest {
    @NotNull
    @NotBlank
    @Schema(description = "모집글 제목", example = "클러버 부원을 모집합니다")
    private String title;

    @NotBlank
    @NotNull
    @Schema(description = "모집글 내용", example = "10/22일부터 클러버 부원을 모집하고 있습니다..")
    private String content;

    @Schema(description = "모집글 이미지 목록", example = "[\"image1\",\"image2\"]")
    private List<String> imageKey;
}
