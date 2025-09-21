package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.QReview;
import com.clubber.domain.domains.review.domain.QReviewLike;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewSortType;
import com.clubber.domain.domains.review.util.ReviewUtil;
import com.clubber.domain.domains.review.vo.ClubReviewResponse;
import com.clubber.domain.domains.user.domain.QUser;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static com.clubber.domain.domains.club.domain.QClub.club;
import static com.clubber.domain.domains.review.domain.QReview.review;
import static com.clubber.domain.domains.review.domain.QReviewKeyword.reviewKeyword;
import static com.clubber.domain.domains.review.domain.QReviewLike.reviewLike;
import static com.clubber.domain.domains.user.domain.QUser.*;

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
    public Page<ClubReviewResponse> queryReviewByClub(Club club, Pageable pageable, ReviewSortType sortType) {

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

        Map<Long, Long> likeCountMap = tuples.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(review).getId(),
                        tuple -> tuple.get(reviewLike.count())
                ));

        List<Review> reviews = tuples.stream()
                .map(tuple -> tuple.get(review))
                .collect(Collectors.toList());

        reviews = queryFactory.selectFrom(review)
                .join(review.reviewKeywords, reviewKeyword).fetchJoin()
                .join(review.user, user).fetchJoin()
                .where(review.id.in(reviews.stream().map(Review::getId).toList()))
                .fetch();

        List<ClubReviewResponse> responses = reviews.stream()
                .map(r -> ClubReviewResponse.of(
                        r,
                        ReviewUtil.extractKeywords(r),
                        likeCountMap.getOrDefault(r.getId(), 0L)
                ))
                .toList();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.countDistinct())
                .from(review)
                .where(review.club.id.eq(club.getId())
                        .and(review.isDeleted.eq(false)));

        return PageableExecutionUtils.getPage(responses, pageable, countQuery::fetchOne);
    }

    // 5️⃣ 동적 정렬 처리
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
    public Optional<Review> findByIdAndNotDeletedApprovedStatus(Long reviewId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(review)
                .where(review.id.eq(reviewId))
                .fetchOne());
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
}
