package com.clubber.ClubberServer.unit.domain.review.domain;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.VerifiedStatus;
import com.clubber.domain.domains.review.exception.ReviewAlreadyDeletedException;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.domain.domains.club.domain.Club;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.*;
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

	private static Review getReview(ApprovedStatus approvedStatus) {
		return Review.builder()
			.id(1L)
			.content("content")
			.approvedStatus(approvedStatus)
			.build();
	}

	private static List<ApprovedStatus> getApprovedStatusListExcept(
		ApprovedStatus excludedApprovedStatus) {
		return Arrays.stream(ApprovedStatus.values())
			.filter(approvedStatus -> approvedStatus != excludedApprovedStatus)
			.collect(Collectors.toList());
	}

	@Test
	@DisplayName("빈 값의 content가 들어왔을 때, content 값은 null, ApprovedStatus은 NULL_CONTENT이다")
	void saveBlankContentReview() {
		//given
		final String blankString = "  ";
		User user = User.builder().id(1L).build();
		Club club = Club.builder().id(1L).build();

		//when
		Review review = Review.of(user, club, blankString, "image");

		//then
		assertAll(
			() -> assertNull(review.getContent()),
			() -> assertEquals(NULL_CONTENT, review.getApprovedStatus())
		);
	}

	@Test
	@DisplayName("이미 삭제된 리뷰를 삭제하면 ReviewAlreadyDeletedException가 발생한다.")
	void deleteAlreadyDeletedReview() {
		//given
		Review review = getReview(DELETED);

		//when & then
		assertThrows(ReviewAlreadyDeletedException.class, review::delete);
	}

	@Test
	@DisplayName("삭제되지 않은 리뷰 상태이면 올바르게 삭제된다.")
	void deleteReviewNotDeletedApprovedStatus() {
		//given
		List<ApprovedStatus> approvedStatusListExceptDeleted = getApprovedStatusListExcept(DELETED);

		//when & then
		approvedStatusListExceptDeleted
			.forEach(approvedStatus -> {
				Review review = getReview(approvedStatus);
				review.delete();
				assertEquals(DELETED, review.getApprovedStatus());
			});
	}

	@Test
	@DisplayName("리뷰 저장시 인증 상태는 기본값이 저장된다")
	void getDefaultReviewVerifiedStatus() {
		Review review = getReview(PENDING);
		assertEquals(VerifiedStatus.NOT_VERIFIED, review.getVerifiedStatus());
	}
}
