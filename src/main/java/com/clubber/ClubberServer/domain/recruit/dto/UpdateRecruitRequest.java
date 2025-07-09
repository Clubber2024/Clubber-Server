package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecruitRequest {

    @NotBlank
    @Schema(description = "모집글 제목", example = "클러버 부원을 모집합니다")
    private String title;

    @NotNull
    @Schema(description = "모집 유형", example = "정규모집")
    private RecruitType recruitType;

    @Schema(description = "모집 시작 일자", example = "2025-07-10 00:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startAt;

    @Schema(description = "모집 종료 일자", example = "2025-07-30 00:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endAt;

    @NotBlank
    @Schema(description = "모집글 내용", example = "10/22일부터 클러버 부원을 모집하고 있습니다..")
    private String content;

    @Schema(description = "지원링크", example = "https://docs.google.com/forms")
    private String applyLink;

    @Schema(description = "캘린더 연동 요청 여부", example = "true")
    private Boolean shouldLinkCalendar;

    @Schema(description = "삭제할 imageurls", example = "[\"https://image.ssuclubber.com/recruit/image1\",\"https://image.ssuclubber.com/recruit/image3\"]")
    private List<String> deletedImageUrls;

    @Schema(description = "새로운 imagekeys", example = "[\"newImage1\",\"newImage2\"]")
    private List<String> newImageKeys;

    @Schema(description = "유지할 imageurls", example = "[\"https://image.ssuclubber.com/recruit/image2\"]")
    private List<String> remainImageUrls;

    @Schema(description = "최종 imageurls 와 imagekeys(배치 순서으로 나열)", example = "[\"newImage1\",\"https://image.ssuclubber.com/recruit/image2\",\"newImage2\"]")
    private List<String> images;

}
