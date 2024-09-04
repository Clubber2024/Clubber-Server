package com.clubber.ClubberServer.domain.review.repository;

import static com.clubber.ClubberServer.domain.club.domain.QClub.*;
import static com.clubber.ClubberServer.domain.review.domain.QReview.*;
import static com.clubber.ClubberServer.domain.review.domain.QReviewKeyword.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.domain.User;
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
	public Page<Review> queryReviewByClub(Club club, Pageable pageable) {

		List<Long> ids = queryFactory.select(review.id)
			.from(review)
			.where(review.club.id.eq(club.getId()))
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
			.where(review.club.id.eq(club.getId()));

		return PageableExecutionUtils.getPage(reviews, pageable, countQuery::fetchOne);
	}
}
