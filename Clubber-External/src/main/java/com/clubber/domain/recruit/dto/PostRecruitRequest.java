package com.clubber.domain.recruit.dto;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.domain.RecruitType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRecruitRequest {

    @NotBlank
    @Schema(description = "모집글 제목", example = "클러버 부원을 모집합니다")
    private String title;

    @NotNull
    @Schema(description = "모집 유형", example = "REGULAR")
    private RecruitType recruitType;

    @Schema(description = "모집 시작 일자", nullable = true, example = "2025-07-10 00:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startAt;

    @Schema(description = "모집 종료 일자", nullable = true, example = "2025-07-30 00:00", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endAt;

    @NotBlank
    @Schema(description = "모집글 내용", example = "10/22일부터 클러버 부원을 모집하고 있습니다..")
    private String content;

    @Schema(description = "지원링크", example = "https://docs.google.com/forms")
    private String applyLink;

    @Schema(description = "모집글 이미지 목록", example = "[\"image1\",\"image2\"]")
    private List<String> imageKey;

    @Schema(description = "캘린더 연동 여부", example = "true")
    private Boolean isCalendarLinked;

    public Recruit toEntity(Club club) {
        return Recruit.builder()
            .title(title)
            .startAt(startAt)
            .endAt(endAt)
            .recruitType(recruitType)
            .content(content)
            .applyLink(applyLink)
            .club(club)
            .build();
    }
}
