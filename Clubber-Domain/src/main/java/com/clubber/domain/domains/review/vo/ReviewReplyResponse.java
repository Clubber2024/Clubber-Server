package com.clubber.domain.domains.review.vo;

import com.clubber.domain.domains.review.domain.ReviewReply;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Optional;

public record ReviewReplyResponse(
        @Schema(description = "답글 id")
        Long id,
        @Schema(description = "리뷰 답글", example = "감사합니다.")
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd")
        LocalDateTime updateAt
) {
    public static ReviewReplyResponse of(ReviewReply reviewReply) {
        return Optional.ofNullable(reviewReply)
                .map(r -> new ReviewReplyResponse(
                        r.getId(),
                        r.getContent(),
                        r.getCreatedAt()
                ))
                .orElse(null);
    }
}
