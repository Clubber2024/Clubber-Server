package com.clubber.domain.report.dto;

import com.clubber.domain.domains.report.domain.Report;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOneReportResponse {

    @Schema(description = "리뷰 id", example = "1")
    private final Long reviewId;

    @Schema(description = "동아리명", example = "클러버")
    private final String clubName;

    @Schema(description = "신고 횟수", example = "2")
    private final Integer reportedNum;

    @Schema(description = "리뷰 내용", example = "좋아요")
    private final String content;

    @Schema(description = "신고사유", example = "허위 정보")
    private final String reportReason;

    @Schema(description = "신고일", example = "2025-07-06T10:00:00")
    private final LocalDateTime reportedAt;


    public static GetOneReportResponse of(Report report, Integer reportedNum) {
        return GetOneReportResponse.builder()
            .reviewId(report.getReview().getId())
            .clubName(report.getReview().getClub().getName())
            .reportedNum(reportedNum)
            .content(report.getReview().getContent())
            .reportReason(report.getReportReason().getTitle())
            .reportedAt(report.getCreatedAt())
            .build();
    }
}
