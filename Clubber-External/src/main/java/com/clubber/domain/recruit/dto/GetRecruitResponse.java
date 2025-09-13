package com.clubber.domain.recruit.dto;

import com.clubber.domain.recruit.domain.Recruit;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetRecruitResponse {

    @Schema(description = "모집글 id", example = "10")
    private final Long recruitId;

    @Schema(description = "모집글 제목", example = "클러버 부원 모집")
    private final String title;

    @Schema(description = "모집 유형", example = "정규모집")
    private final String recruitType;

    @Schema(description = "모집글 내용", example = "숭실대학교 클러버 부원 모집을 시작...")
    private final String content;

    @Schema(description = "지원링크", example = "https://docs.google.com/forms")
    private final String applyLink;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    @Schema(description = "모집글 생성 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    public static GetRecruitResponse from(Recruit recruit) {
        return GetRecruitResponse.builder()
            .recruitId(recruit.getId())
            .title(recruit.getTitle())
            .recruitType(recruit.getRecruitType().getTitle())
            .content(recruit.getContent())
            .applyLink(recruit.getApplyLink())
            .totalView(recruit.getTotalView())
            .createdAt(recruit.getCreatedAt())
            .build();
    }
}
