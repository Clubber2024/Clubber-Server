package com.clubber.ClubberServer.domain.review.repository;

import static com.clubber.ClubberServer.domain.club.domain.QClub.club;
import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.DELETED;
import static com.clubber.ClubberServer.domain.review.domain.QReview.review;
import static com.clubber.ClubberServer.domain.review.domain.QReviewKeyword.reviewKeyword;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.VerifiedStatus;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Review> queryReviewByUserOrderByIdDesc(User user) {
		return queryFactory.selectFrom(review)
			.join(review.reviewKeywords, reviewKeyword).fetchJoin()
			.join(review.club, club).fetchJoin()
			.where(review.user.eq(user)
				.and(review.approvedStatus.ne(DELETED)))
			.orderBy(review.id.desc())
			.fetch();
	}

	@Override
	public Page<Review> queryReviewByClub(Club club, Pageable pageable,
		ApprovedStatus approvedStatus, VerifiedStatus verifiedStatus) {

		/**
		 * 커버링 인덱스 적용
		 */

		List<Long> ids = queryFactory.select(review.id)
			.from(review)
			.where(review.club.id.eq(club.getId())
				.and(review.approvedStatus.ne(DELETED))
				.and(eqApprovedStatus(approvedStatus))
				.and(eqVerifiedStatus(verifiedStatus))
			)
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
			.where(review.club.id.eq(club.getId())
				.and(review.approvedStatus.ne(DELETED))
				.and(eqApprovedStatus(approvedStatus))
				.and(eqVerifiedStatus(verifiedStatus))
			);

		return PageableExecutionUtils.getPage(reviews, pageable, countQuery::fetchOne);
	}

	private BooleanExpression eqApprovedStatus(ApprovedStatus approvedStatus) {
		if (approvedStatus == null) {
			return null;
		}
		return review.approvedStatus.eq(approvedStatus);
	}

	private BooleanExpression eqVerifiedStatus(VerifiedStatus verifiedStatus) {
		if (verifiedStatus == null) {
			return null;
		}
		return review.verifiedStatus.eq(verifiedStatus);
	}

	@Override
	public List<Review> queryReviewNoOffsetByClub(Club club, Pageable pageable, Long reviewId,
		ApprovedStatus approvedStatus) {
		return queryFactory.selectFrom(review)
			.where(review.club.id.eq(club.getId()),
				ltReviewId(reviewId),
				approvedStatusEq(approvedStatus))
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

	private BooleanExpression approvedStatusEq(ApprovedStatus approvedStatus) {
		if (approvedStatus == null) {
			return null;
		}
		return review.approvedStatus.eq(approvedStatus);
	}

	@Override
	public boolean existsByClubAndUserAndNotApprovedStatusDeleted(Club club, User user) {
		return queryFactory.selectOne()
			.from(review)
			.where(review.club.id.eq(club.getId())
				.and(review.user.id.eq(user.getId()))
				.and(review.approvedStatus.ne(DELETED))
			)
			.fetchFirst() != null;
	}

	@Override
	public Optional<Review> findByIdAndNotDeletedApprovedStatus(Long reviewId) {
		return Optional.ofNullable(queryFactory
			.selectFrom(review)
			.where(review.id.eq(reviewId)
				.and(review.approvedStatus.ne(DELETED)))
			.fetchOne());
	}
}
