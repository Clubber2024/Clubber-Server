package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
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
public class GetOneRecruitWithClubResponse {

    @Schema(description = "club id", example = "1")
    private final Long clubId;

    @Schema(description = "club명", example = "클러버")
    private final String clubName;

    @Schema(description = "clubType", example = "중앙동아리")
    private final String clubType;

    @Schema(description = "club 대표 이미지", example = "https://image.ssuclubber.com/club/image1")
    private final ImageVO clubImage;

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

    @Schema(description = "모집글 imageUrls", example = "[\"https://image.ssuclubber.com/recruit/image1\",\"https://image.ssuclubber.com/recruit/image3\"]")
    private final List<ImageVO> imageUrls;

    @Schema(description = "캘린더 연동 여부", example = "true")
    private final Boolean isCalendarLinked;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    @Schema(description = "모집글 생성 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    public static GetOneRecruitWithClubResponse of(Recruit recruit, Club club,
        List<ImageVO> images) {
        return GetOneRecruitWithClubResponse.builder()
            .clubId(club.getId())
            .clubName(club.getName())
            .clubType(club.getClubType().getTitle())
            .clubImage(club.getImageUrl())
            .recruitId(recruit.getId())
            .title(recruit.getTitle())
            .recruitType(recruit.getRecruitType().getTitle())
            .content(recruit.getContent())
            .applyLink(recruit.getApplyLink())
            .imageUrls(images)
            .isCalendarLinked(recruit.isCalendarLinked())
            .totalView(recruit.getTotalView())
            .createdAt(recruit.getCreatedAt())
            .build();
    }
}