package com.clubber.domain.review.dto;

import com.clubber.domain.domains.review.domain.ReportStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubReviewResponse {

    @Schema(description = "리뷰 id", example = "1")
    private final Long reviewId;

    @Schema(description = "유저 id", example = "1")
    private final Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @Schema(description = "리뷰 작성 일자", example = "2024.01.01", type = "string")
    private final LocalDateTime dateTime;

    @Schema(description = "작성한 리뷰 키워드")
    private final Set<String> keywords;

    @Schema(description = "해당 동아리 된 한줄평 중 승인된 한줄평의 내용", example = "활동이 재밌어요")
    private final String content;

    @Schema(description = "리뷰 좋아요 수")
    private final Long likes;

    @Schema(description = "신고 상태")
    private final ReportStatus reportStatus;

    public static ClubReviewResponse of(Review review, Set<String> keywords) {
        return ClubReviewResponse.builder()
                .keywords(keywords)
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .dateTime(review.getCreatedAt())
                .content(review.getContent())
                .likes(review.getLikes())
                .reportStatus(review.getReportStatus())
                .build();
    }
}
