package com.clubber.domain.user.dto;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.review.domain.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetUserReviewReportResponse(
        String clubName,
        String content,
        String reportReason,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        LocalDateTime reportDate
) {
        public static GetUserReviewReportResponse of(Report report, Review review, Club club) {
                return GetUserReviewReportResponse.builder()
                        .clubName(club.getName())
                        .content(review.getContent())
                        .reportReason(report.getReportReason().getTitle())
                        .reportDate(report.getCreatedAt())
                        .build();
        }
}
