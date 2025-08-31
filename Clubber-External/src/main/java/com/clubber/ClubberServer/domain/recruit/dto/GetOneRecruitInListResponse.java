package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOneRecruitInListResponse {

    @Schema(description = "모집글 id", example = "10")
    private final Long recruitId;

    @Schema(description = "모집글 제목", example = "클러버 부원 모집")
    private final String title;

    @Schema(description = "모집 유형", example = "정규모집")
    private final String recruitType;

    @Schema(description = "모집 시작 일자", example = "2025-07-06T10:00:00")
    private final LocalDateTime startAt;

    @Schema(description = "모집 종료 일자", example = "2025-07-12T12:00:00")
    private final LocalDateTime endAt;

    @Schema(description = "모집글 내용", example = "숭실대학교 클러버 부원 모집을 시작...")
    private final String content;

    @Schema(description = "지원링크", example = "https://docs.google.com/forms")
    private final String applyLink;

    @Schema(description = "모집글 대표 이미지", example = "https://image.ssuclubber.com/club/image2")
    private final ImageVO imageUrl;

    public static GetOneRecruitInListResponse of(Recruit recruit, ImageVO imageUrl) {
        return GetOneRecruitInListResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .recruitType(recruit.getRecruitType().getTitle())
                .startAt(recruit.getStartAt())
                .endAt(recruit.getEndAt())
                .content(recruit.getContent())
                .applyLink(recruit.getApplyLink())
                .imageUrl(imageUrl)
                .build();
    }
}
