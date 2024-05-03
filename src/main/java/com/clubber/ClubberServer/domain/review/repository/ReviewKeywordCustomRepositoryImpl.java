package com.clubber.ClubberServer.domain.review.repository;

import static com.clubber.ClubberServer.domain.review.domain.QReview.review;
import static com.clubber.ClubberServer.domain.review.domain.QReviewKeyword.reviewKeyword;


import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
}
