package com.clubber.domain.review.dto;

import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateReviewReportResponse {

    @Schema(description = "신고된 리뷰 id", example = "12")
    private final Long reviewId;

    @Schema(description = "접수된 신고 id", example = "1")
      private final Long reportId;

    @Schema(description = "신고 사유", example = "활동이 재밌어요")
    private final String reportReason;
    
    public static CreateReviewReportResponse of(Review review, Report report) {
        return CreateReviewReportResponse.builder()
            .reviewId(review.getId())
            .reportId(report.getId())
            .reportReason(report.getReportReason().getTitle())
            .build();
    }

}
