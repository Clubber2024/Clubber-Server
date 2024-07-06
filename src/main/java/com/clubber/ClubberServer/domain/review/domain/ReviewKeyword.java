package com.clubber.ClubberServer.domain.review.domain;


import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    private Keyword keyword;

    @Builder
    private ReviewKeyword(Long id, Review review, Keyword keyword) {
        this.id = id;
        this.review = review;
        this.keyword = keyword;
    }

    public static ReviewKeyword of(Review review, Keyword keyword){
        return ReviewKeyword.builder()
                .review(review)
                .keyword(keyword)
                .build();
    }

    public static Set<Keyword> from(List<ReviewKeyword> reviewKeywords){
        return reviewKeywords.stream()
                .map(ReviewKeyword::getKeyword)
                .collect(Collectors.toSet());
    }

}
