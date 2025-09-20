package com.clubber.domain.domains.review.domain;

import com.clubber.domain.domains.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder.Default
    private boolean isDeleted = false;

    public void delete() {
        isDeleted = true;
    }
}
