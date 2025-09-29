package com.clubber.domain.domains.review.vo;

import com.clubber.domain.domains.review.domain.ReviewReply;

import java.time.LocalDateTime;
import java.util.Optional;

public record ReviewReplyVO(
        Long id,
        String content,
        LocalDateTime updateAt
) {
    public static ReviewReplyVO of(ReviewReply reviewReply) {
        return Optional.ofNullable(reviewReply)
                .map(r -> new ReviewReplyVO(
                        r.getId(),
                        r.getContent(),
                        r.getCreatedAt()
                ))
                .orElse(null);
    }
}
