package com.clubber.ClubberServer.domain.review.repository;

import static com.clubber.ClubberServer.domain.club.domain.QClub.club;
import static com.clubber.ClubberServer.domain.review.domain.QReview.review;
import static com.clubber.ClubberServer.domain.review.domain.QReviewKeyword.reviewKeyword;

import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.response.KeywordStat;
import com.querydsl.core.types.Projections;
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

    @Override
    public List<KeywordStat> queryReviewKeywordStatsByClubId(Long clubId) {
        return queryFactory
                .select(Projections.fields(KeywordStat.class,
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

    /**
     *fetch join에서 on을 지원하지 않아 outer join으로 구현 실패 : 추후에 방법 찾아보기  
     */
//    @Override
//    public List<ReviewKeyword> queryReviewByClubId(Long clubId) {
//        return queryFactory
//                .select(reviewKeyword)
//                .from(reviewKeyword)
//                .leftJoin(reviewKeyword.review).fetchJoin()
//                //.on(review.approvedStatus.eq(ApprovedStatus.APPROVED))
//                .where(review.club.id.eq(clubId))
//                .fetch();
//    }


}
