package com.clubber.ClubberServer.unit.domain.review.domain;

import com.clubber.domain.domains.review.domain.DeletionStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.VerifiedStatus;
import com.clubber.domain.domains.review.exception.ReviewAlreadyDeletedException;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.domains.club.domain.Club;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewDomainTest {

//	@Test
//	@DisplayName("Review에 Keyword 리스트를 저장할때, ReviewKeyword에 포함되어 저장된다.")
//	void addReviewKeywordsTest() {
//		//given
//		Review review = getReview(APPROVED);
//		List<Keyword> keywords = List.of(CULTURE, FEE, ACTIVITY, CAREER, MANAGE);
//
//		//when
//		review.addKeywords(keywords);
//
//		//then
//		List<ReviewKeyword> reviewKeywords = review.getReviewKeywords();
//		Assertions.assertThat(reviewKeywords)
//			.extracting(ReviewKeyword::getKeyword)
//			.containsExactly(CULTURE, FEE, ACTIVITY, CAREER, MANAGE);
//	}

//	private static Review getReview(DeletionStatus deletionStatus) {
//		return Review.builder()
//			.id(1L)
//			.content("content")
//			.approvedStatus(deletionStatus)
//			.build();
//	}
//
//	private static List<DeletionStatus> getApprovedStatusListExcept(
//		DeletionStatus excludedDeletionStatus) {
//		return Arrays.stream(DeletionStatus.values())
//			.filter(approvedStatus -> approvedStatus != excludedDeletionStatus)
//			.collect(Collectors.toList());
//	}
//
//	@Test
//	@DisplayName("이미 삭제된 리뷰를 삭제하면 ReviewAlreadyDeletedException가 발생한다.")
//	void deleteAlreadyDeletedReview() {
//		//given
//		Review review = getReview(DELETED);
//
//		//when & then
//		assertThrows(ReviewAlreadyDeletedException.class, review::delete);
//	}
//
//	@Test
//	@DisplayName("삭제되지 않은 리뷰 상태이면 올바르게 삭제된다.")
//	void deleteReviewNotDeletedApprovedStatus() {
//		//given
//		List<DeletionStatus> deletionStatusListExceptDeleted = getApprovedStatusListExcept(DELETED);
//
//		//when & then
//		deletionStatusListExceptDeleted
//			.forEach(approvedStatus -> {
//				Review review = getReview(approvedStatus);
//				review.delete();
//				assertEquals(DELETED, review.getDeletionStatus());
//			});
//	}
//
//	@Test
//	@DisplayName("리뷰 저장시 인증 상태는 기본값이 저장된다")
//	void getDefaultReviewVerifiedStatus() {
//		Review review = getReview(PENDING);
//		assertEquals(VerifiedStatus.NOT_VERIFIED, review.getVerifiedStatus());
//	}
}
