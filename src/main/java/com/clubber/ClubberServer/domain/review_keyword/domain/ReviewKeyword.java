package com.clubber.ClubberServer.domain.review_keyword.domain;


import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.keyword.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewKeyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    @NotNull
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    @NotNull
    private Keyword keyword;

    @Builder
    private ReviewKeyword(Long id, Review review, Keyword keyword) {
        this.id = id;
        this.review = review;
        this.keyword = keyword;
    }
}
