package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminsPendingReviews {

    @Schema(description = "리뷰 id", example = "1")
    private final Long reviewId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    @Schema(description = "리뷰 작성 일자", example = "2024.01.01", type = "string")
    private final LocalDateTime writtenDate;


    @Schema(description = "한줄평", example = "분위기가 좋아요")
    private final String content;

    public static List<GetAdminsPendingReviews> from(List<Review> reviews){
        return reviews.stream()
                .map((r) -> GetAdminsPendingReviews.from(r))
                .collect(Collectors.toList());

    }

    private static GetAdminsPendingReviews from(Review review){
        return GetAdminsPendingReviews.builder()
                .reviewId(review.getId())
                .writtenDate(review.getCreatedAt())
                .content(review.getContent())
                .build();
    }
}
