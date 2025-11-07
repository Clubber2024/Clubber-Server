package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.review.domain.*;
import com.clubber.domain.domains.review.util.ReviewUtil;
import com.clubber.domain.domains.review.vo.ClubReviewResponse;
import com.clubber.domain.domains.review.vo.ReviewReplyResponse;
import com.clubber.domain.domains.user.domain.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.clubber.domain.domains.club.domain.QClub.club;
import static com.clubber.domain.domains.report.domain.QReport.report;
import static com.clubber.domain.domains.review.domain.QReview.review;
import static com.clubber.domain.domains.review.domain.QReviewKeyword.reviewKeyword;
import static com.clubber.domain.domains.review.domain.QReviewLike.reviewLike;
import static com.clubber.domain.domains.review.domain.QReviewReply.reviewReply;
import static com.clubber.domain.domains.user.domain.QUser.user;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> queryReviewByUserOrderByIdDesc(User user) {
        return queryFactory.selectFrom(review)
                .join(review.reviewKeywords, reviewKeyword).fetchJoin()
                .join(review.club, club).fetchJoin()
                .where(review.user.eq(user)
                        .and(review.isDeleted.eq(false)))
                .orderBy(review.id.desc())
                .fetch();
    }

    @Override
    public Page<ClubReviewResponse> queryReviewByClub(Club club, User loginUser, Pageable pageable, ReviewSortType sortType) {

        List<Tuple> tuples = queryFactory
                .select(review, reviewLike.count())
                .from(review)
                .leftJoin(review.reviewLikes, reviewLike)
                .on(reviewLike.isDeleted.eq(false))
                .where(review.club.id.eq(club.getId())
                        .and(review.isDeleted.eq(false)))
                .groupBy(review)
                .orderBy(getOrderSpecifier(sortType, review, reviewLike))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> reviewIds = tuples.stream()
                .map(t -> t.get(review).getId())
                .toList();

        Map<Long, Long> likeCountMap = tuples.stream()
                .collect(Collectors.toMap(
                        t -> t.get(review).getId(),
                        t -> t.get(reviewLike.count())
                ));


        List<Long> likedReviewIds = queryFactory.select(reviewLike.review.id)
                .from(reviewLike)
                .where(reviewLike.user.id.eq(loginUser.getId())
                        .and(reviewLike.isDeleted.eq(false))
                        .and(reviewLike.review.id.in(reviewIds))
                )
                .fetch();

        List<Review> reviews = queryFactory.selectFrom(review)
                .join(review.reviewKeywords, reviewKeyword).fetchJoin()
                .join(review.user, user).fetchJoin()
                .where(review.id.in(reviewIds))
                .fetch();

        // Tuple 순서에 맞게 재정렬
        Map<Long, Review> reviewMap = reviews.stream()
                .collect(Collectors.toMap(Review::getId, r -> r));

        List<Review> sortedReviews = reviewIds.stream()
                .map(reviewMap::get)
                .toList();

        List<ClubReviewResponse> responses = sortedReviews.stream()
                .map(r -> ClubReviewResponse.of(
                        r,
                        ReviewUtil.extractKeywords(r),
                        likeCountMap.getOrDefault(r.getId(), 0L),
                        likedReviewIds.contains(r.getId()),
                        ReviewReplyResponse.of(r.getReviewReply())

                ))
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(review.club.id.eq(club.getId())
                        .and(review.isDeleted.eq(false)));

        return PageableExecutionUtils.getPage(responses, pageable, countQuery::fetchOne);
    }

    public Page<Review> queryReviewByClubAndFilterType(Club club, ReviewFilterType reviewFilterType, Pageable pageable) {
        List<Review> reviews = queryFactory.selectFrom(review)
                .leftJoin(review.reviewReply, reviewReply)
                .where(review.club.id.eq(club.getId())
                        .and(eqReviewReplyNull(reviewFilterType))
                        .and(review.isDeleted.eq(false))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(review.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .leftJoin(review.reviewReply, reviewReply)
                .where(review.club.id.eq(club.getId())
                        .and(eqReviewReplyNull(reviewFilterType))
                        .and(review.isDeleted.eq(false))
                );

        return PageableExecutionUtils.getPage(reviews, pageable, countQuery::fetchOne);
    }

    //TODO EXISTS 쿼리 고려
    private BooleanExpression eqReviewReplyNull(ReviewFilterType filterType) {
        if (filterType == ReviewFilterType.NOT_REPLYED) {
            return review.reviewReply.isNull();
        }
        return null;
    }

    private static OrderSpecifier<?> getOrderSpecifier(
            ReviewSortType sortType,
            QReview review,
            QReviewLike reviewLike
    ) {
        return switch (sortType) {
            case ASC -> review.id.asc();
            case DESC -> review.id.desc();
            case LIKE -> reviewLike.count().coalesce(0L).desc();
        };
    }

    @Override
    public List<Review> queryReviewNoOffsetByClub(Club club, Pageable pageable, Long reviewId) {
        return queryFactory.selectFrom(review)
                .where(review.club.id.eq(club.getId()),
                        ltReviewId(reviewId))
                .orderBy(review.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }

    private BooleanExpression ltReviewId(Long reviewId) {
        if (reviewId == null) {
            return null;
        }
        return review.id.lt(reviewId);
    }

    @Override
    public boolean existsByClubAndUser(Club club, User user) {
        return queryFactory.selectOne()
                .from(review)
                .where(review.club.id.eq(club.getId())
                        .and(review.user.id.eq(user.getId()))
                        .and(review.isDeleted.eq(false))
                )
                .fetchFirst() != null;
    }

    @Override
    public void softDeleteReviewByClubId(Long clubId) {
        queryFactory.update(review)
                .set(review.isDeleted, true)
                .where(
                        review.club.id.eq(clubId), review.isDeleted.eq(false)
                )
                .execute();
    }

    public List<Report> queryNextReviewReport(Long reviewId, Long nowReviewReportId) {
        return queryFactory.selectFrom(report)
                .join(report.review, review).fetchJoin()
                .where(report.review.id.eq(reviewId)
                        .and(report.isDeleted.eq(false))
                        .and(ltReviewReportId(nowReviewReportId)))
                .limit(2)
                .orderBy(report.id.desc())
                .fetch();
    }

    private BooleanExpression ltReviewReportId(Long nowReviewReportId) {
        if (nowReviewReportId == null) {
            return null;
        }
        return report.id.lt(nowReviewReportId);
    }
}
