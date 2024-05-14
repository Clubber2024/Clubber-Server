package com.clubber.ClubberServer.domain.review.repository;

import static com.clubber.ClubberServer.domain.club.domain.QClub.club;
import static com.clubber.ClubberServer.domain.review.domain.QReview.review;
import static com.clubber.ClubberServer.domain.review.domain.QReviewKeyword.reviewKeyword;


import com.clubber.ClubberServer.domain.club.domain.QClub;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.KeywordStats;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewKeywordCustomRepositoryImpl implements ReviewKeywordCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewKeyword> queryReviewKeywordByClubId(Long clubId) {
        return queryFactory.selectFrom(reviewKeyword)
                .join(reviewKeyword.review, review).fetchJoin()
                .where(review.club.id.eq(clubId))
                .fetch();
    }

    @Override
    public List<KeywordStats> queryReviewKeywordStatsByClubId(Long clubId) {

        return queryFactory
                .select(Projections.fields(KeywordStats.class,
                        reviewKeyword.keyword, reviewKeyword.count().as("count")))
                .from(reviewKeyword)
                .where(review.club.id.eq(clubId))
                .join(reviewKeyword.review, review)
                .groupBy(reviewKeyword.keyword)
                .fetch();
    }

    @Override
    public List<ReviewKeyword> queryReviewKeywordByUserId(Long userId) {
        return queryFactory
                .selectFrom(reviewKeyword)
                .from(reviewKeyword)
                .join(reviewKeyword.review, review).fetchJoin()
                .join(review.club, club).fetchJoin()
                .where(review.user.id.eq(userId))
                .fetch();
    }


}
