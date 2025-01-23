package com.clubber.ClubberServer.domain.review.repository;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.DELETED;
import static com.clubber.ClubberServer.domain.review.domain.QReview.review;
import static com.clubber.ClubberServer.domain.review.domain.QReviewKeyword.reviewKeyword;

import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewKeywordCustomRepositoryImpl implements ReviewKeywordCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<KeywordCountStatDTO> queryReviewKeywordStatsByClubId(Long clubId) {
		return queryFactory
			.select(Projections.fields(KeywordCountStatDTO.class,
				reviewKeyword.keyword, reviewKeyword.count().as("count")))
			.from(reviewKeyword)
			.where(review.club.id.eq(clubId)
				.and(review.approvedStatus.ne(DELETED)))
			.join(reviewKeyword.review, review)
			.groupBy(reviewKeyword.keyword)
			.fetch();
	}
}
