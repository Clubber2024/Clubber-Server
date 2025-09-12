package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.common.vo.image.ImageVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOneAdminRecruitResponse {

    @Schema(description = "club id", example = "1")
    private final Long clubId;

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

    @Schema(description = "모집글 imageUrls", example = "[\"https://image.ssuclubber.com/club/image1\",\"https://image.ssuclubber.com/club/image3\"]")
    private final List<ImageVO> imageUrls;

    @Schema(description = "캘린더 연동 여부", example = "true")
    private Boolean isCalendarLinked;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    @Schema(description = "모집글 생성 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    @Schema(description = "현재 모집 상태", example = "모집전,진행중,마감됨")
    private final String recruitStatus;

    public static GetOneAdminRecruitResponse of(Recruit recruit, List<ImageVO> images) {
        return GetOneAdminRecruitResponse.builder()
                .clubId(recruit.getClub().getId())
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .recruitType(recruit.getRecruitType().getTitle())
                .startAt(recruit.getStartAt())
                .endAt(recruit.getEndAt())
                .content(recruit.getContent())
                .applyLink(recruit.getApplyLink())
                .imageUrls(images)
                .isCalendarLinked(recruit.isCalendarLinked())
                .totalView(recruit.getTotalView())
                .createdAt(recruit.getCreatedAt())
                .recruitStatus(recruit.getStatus())
                .build();
    }
}
