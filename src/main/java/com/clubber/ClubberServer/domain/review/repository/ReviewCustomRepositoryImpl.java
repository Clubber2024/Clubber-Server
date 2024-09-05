package com.clubber.ClubberServer.domain.review.repository;

import static com.clubber.ClubberServer.domain.club.domain.QClub.*;
import static com.clubber.ClubberServer.domain.review.domain.QReview.*;
import static com.clubber.ClubberServer.domain.review.domain.QReviewKeyword.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Review> queryReviewByUserOrderByIdDesc(User user) {
		return queryFactory.selectFrom(review)
			.join(review.reviewKeywords, reviewKeyword).fetchJoin()
			.join(review.club, club).fetchJoin()
			.where(review.user.eq(user))
			.orderBy(review.id.desc())
			.fetch();
	}

	@Override
	public Page<Review> queryReviewByClub(Club club, Pageable pageable, ApprovedStatus approvedStatus) {

		List<Long> ids = queryFactory.select(review.id)
			.from(review)
			.where(review.club.id.eq(club.getId()),
				approvedStatusEq(approvedStatus))
			.orderBy(review.id.desc())
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
			.where(review.club.id.eq(club.getId()),
				approvedStatusEq(approvedStatus));

		return PageableExecutionUtils.getPage(reviews, pageable, countQuery::fetchOne);
	}

	@Override
	public List<Review> queryReviewNoOffsetByClub(Club club, Pageable pageable, Long reviewId) {
		return queryFactory.selectFrom(review)
			.where(review.club.id.eq(club.getId()),
				ltReviewId(reviewId))
			.orderBy(review.id.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private BooleanExpression ltReviewId(Long reviewId){
		if(reviewId == null){
			return null;
		}
		return review.id.lt(reviewId);
	}

	private BooleanExpression approvedStatusEq(ApprovedStatus approvedStatus) {
		return approvedStatus == null ? null : review.approvedStatus.eq(approvedStatus);
	}
}
