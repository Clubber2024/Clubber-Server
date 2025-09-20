package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.*;
import com.clubber.domain.domains.user.domain.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.clubber.domain.domains.club.domain.QClub.club;
import static com.clubber.domain.domains.review.domain.QReview.review;
import static com.clubber.domain.domains.review.domain.QReviewKeyword.reviewKeyword;

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
    public Page<Review> queryReviewByClub(Club club, Pageable pageable, ReviewSortType sortType) {

        /**
         * 커버링 인덱스 적용
         */

        List<Long> ids = queryFactory.select(review.id)
                .from(review)
                .where(review.club.id.eq(club.getId())
                        .and(review.isDeleted.eq(false)))
                .orderBy(getSort(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Review> reviews = queryFactory.selectFrom(review)
                .join(review.reviewKeywords, reviewKeyword).fetchJoin()
                .where(review.id.in(ids))
                .orderBy(review.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(review.count())
                .from(review)
                .where(review.id.in(ids));

        return PageableExecutionUtils.getPage(reviews, pageable, countQuery::fetchOne);
    }

    private static OrderSpecifier<?> getSort(ReviewSortType sortType) {
        QReview review = QReview.review;
        return switch (sortType) {
            case ASC -> review.id.asc();
            case DESC -> review.id.desc();
            default -> review.id.desc();
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
    public boolean existsByClubAndUserAndNotApprovedStatusDeleted(Club club, User user) {
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
