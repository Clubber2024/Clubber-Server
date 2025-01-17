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
public class UpdateRecruitRequest {

    @NotNull
    @NotBlank
    @Schema(description = "모집글 제목", example = "클러버 부원을 모집합니다")
    private String title;

    @NotNull
    @NotBlank
    @Schema(description = "모집글 내용", example = "10/22일부터 클러버 부원을 모집하고 있습니다..")
    private String content;

    @Schema(description = "삭제할 imageurls",  example = "[\"www.clubber/amazon/club/image1\",\"www.clubber/amazon/club/image3\"]")
    private List<String> deletedImageUrls;

    @Schema(description = "새로운 imagekeys", example = "[\"newImage1\",\"newImage2\"]")
    private List<String> newImageKeys;

    @Schema(description = "유지할 imageurls", example = "[\"www.clubber/amazon/club/image2\"]")
    private List<String> remainImageUrls;

    @Schema(description = "최종 imageurls 와 imagekeys(배치 순서으로 나열)", example = "[\"newImage1\",\"www.clubber/amazon/club/image2\",\"newImage2\"]")
    private List<String> images;

}
