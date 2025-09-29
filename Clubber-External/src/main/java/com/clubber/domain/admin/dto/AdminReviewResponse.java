package com.clubber.domain.admin.dto;

import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewReply;
import com.clubber.domain.domains.review.vo.ReviewReplyVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminReviewResponse {

    @Schema(description = "리뷰 id", example = "1")
    private final Long reviewId;

    @Schema(description = "작성한 리뷰 키워드",
            example = "[\"CULTURE\", \"FEE\", \"ACTIVITY\", \"CAREER\", \"MANAGE\"]")
    private final Set<String> keywords;

    @Schema(description = "리뷰 한줄평", example = "분위기가 좋아요")
    private final String content;

    @Schema(description = "리뷰 작성 일자", example = "2024.01.01", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private final LocalDateTime dateTime;

    @Schema(description = "리뷰 답글", example = "감사합니다.")
    private final String reply;

    @Schema(description = "리뷰 답글 작성 일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd")
    private LocalDateTime repliedDate;

    public static AdminReviewResponse of(Review review, Set<String> keywords, ReviewReplyVO reviewReply) {
        return AdminReviewResponse.builder()
                .reviewId(review.getId())
                .keywords(keywords)
                .content(review.getContent())
                .dateTime(review.getCreatedAt())
                .reply(reviewReply.content())
                .repliedDate(reviewReply.updateAt())
                .build();
    }
}
