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
public class GetOneReportDetailResponse {

    @Schema(description = "신고 id", example = "12")
    private final Long reportId;

    @Schema(description = "동아리명", example = "클러버")
    private final String clubName;

    @Schema(description = "작성자", example = "홍길동")
    private final String writer;

    @Schema(description = "리뷰 id", example = "1")
    private final Long reviewId;

    @Schema(description = "리뷰 내용", example = "좋아요")
    private final String content;

    @Schema(description = "신고사유", example = "허위 정보")
    private final String reportReason;

    @Schema(description = "구체사유", example = "해당 동아리와 관련없는 내용입니다.")
    private final String detailReason;

    @Schema(description = "신고 일자", example = "2025-07-06T10:00:00")
    private final LocalDateTime reportedAt;

    public static GetOneReportDetailResponse from(Report report) {
        return GetOneReportDetailResponse.builder()
            .reportId(report.getId())
            .clubName(report.getReview().getClub().getName())
            .writer(report.getReview().getUser().getEmail())
            .reviewId(report.getReview().getId())
            .content(report.getReview().getContent())
            .reportReason(report.getReportReason().getTitle())
            .detailReason(report.getDetailReason())
            .reportedAt(report.getCreatedAt())
            .build();
    }

}
