package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.review.domain.ReportStatus;
import com.clubber.domain.domains.review.vo.KeywordCountStatDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.clubber.domain.domains.review.domain.QReview.review;
import static com.clubber.domain.domains.review.domain.QReviewKeyword.reviewKeyword;

@RequiredArgsConstructor
public class ReviewKeywordCustomRepositoryImpl implements ReviewKeywordCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<KeywordCountStatDto> queryReviewKeywordStatsByClubId(Long clubId) {
        return queryFactory
                .select(Projections.constructor(
                        KeywordCountStatDto.class,
                        reviewKeyword.keyword,
                        reviewKeyword.count().as("count"))
                )
                .from(reviewKeyword)
                .where(review.club.id.eq(clubId)
                        .and(review.reportStatus.eq(ReportStatus.VISIBLE)
                                .and(review.isDeleted.eq(false)))
                )
                .join(reviewKeyword.review, review)
                .groupBy(reviewKeyword.keyword)
                .orderBy(reviewKeyword.count().desc())
                .limit(5)
                .fetch();
    }
}
