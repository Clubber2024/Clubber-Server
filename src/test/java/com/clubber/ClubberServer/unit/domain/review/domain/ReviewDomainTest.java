package com.clubber.ClubberServer.unit.domain.review.domain;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.APPROVED;
import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.NULL_CONTENT;
import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.PENDING;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.clubber.ClubberServer.domain.admin.exception.InvalidApprovedStatusException;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.domain.User;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewDomainTest {

	// 일반 사용자 content 조회
	@Test
	@DisplayName("승인 상태의 댓글이 아니라면 사용자들에게 보여주는 content는 null이 반환된다.")
	void getPendingReviewContentForUser() {
		//given
		List<ApprovedStatus> approvedStatusExceptApproved = getApprovedStatusListExcept(APPROVED);

		//when & then
		approvedStatusExceptApproved
			.stream()
			.forEach(approvedStatus -> {
				Review review = getReview(approvedStatus);
				assertNull(review.getContentForUser());
			});
	}

	@Test
	@DisplayName("승인 상태의 댓글이라면, 사용자들에게 content 자체로 반환된다.")
	void getApprovedReviewContentForUser() {
		//given
		Review review = getReview(APPROVED);

		//when
		String contentForUser = review.getContentForUser();

		//then
		assertEquals(contentForUser, "content");
	}

	// 수동 승인
	@Test
	@DisplayName("승인 대기 상태인 댓글은, 승인 작업 이후에 승인 완료 상태로 바뀐다.")
	void updateReviewPendingApprovedStatus() {
		//given
		Review review = getReview(PENDING);

		//when
		review.updateReviewStatus(APPROVED);

		//then
		assertEquals(review.getApprovedStatus(), APPROVED);
	}

	@Test
	@DisplayName("승인 대기 상태의 댓글이 아닌 경우, InvalidApprovedStatusException가 발생한다.")
	void updateReviewApprovedStatusExceptPending() {
		//given
		List<ApprovedStatus> approvedStatusExceptPending = getApprovedStatusListExcept(PENDING);

		//when & then
		approvedStatusExceptPending.stream()
			.forEach(approvedStatus -> {
				Review review = getReview(approvedStatus);
				assertThrows(InvalidApprovedStatusException.class,
					() -> review.updateReviewStatus(approvedStatus));
			});
	}

	@Test
	@DisplayName("승인 대기 상태의 댓글의 경우에, 자동 승인이 수행된다.")
	void updateAutoReviewApprovedPendingStatus() {
		//given
		Review review = getReview(PENDING);

		//when
		review.autoUpdateReviewStatus();

		//then
		assertEquals(review.getApprovedStatus(), APPROVED);
	}

	@Test
	@DisplayName("승인 대기 상태의 댓글이 아닌 경우, 자동 승인이 수행되지 않아 기존 승인 상태가 된다.")
	void updateAutoReviewApprovedExceptPendingStatus() {
		//given
		List<ApprovedStatus> approvedStatusListExceptPending = getApprovedStatusListExcept(PENDING);

		//when & then
		approvedStatusListExceptPending.stream()
			.forEach(approvedStatus -> {
				Review review = getReview(approvedStatus);
				review.autoUpdateReviewStatus();
				assertEquals(review.getApprovedStatus(), approvedStatus);
			});
	}

	@Test
	@DisplayName("빈 값의 content가 들어왔을 때, content 값은 null, ApprovedStatus은 NULL_CONTENT이다")
	void saveBlankContentReview() {
		//given
		final String blankString = "  ";
		User user = User.builder().id(1L).build();
		Club club = Club.builder().id(1L).build();

		//when
		Review review = Review.of(user, club, blankString);

		//then
		assertAll(
			() -> assertEquals(review.getContent(), null),
			() -> assertEquals(review.getApprovedStatus(), NULL_CONTENT)
		);
	}

	private static List<ApprovedStatus> getApprovedStatusListExcept(
		ApprovedStatus excludedApprovedStatus) {
		return Arrays.stream(ApprovedStatus.values())
			.filter(approvedStatus -> approvedStatus != excludedApprovedStatus)
			.collect(Collectors.toList());
	}

	private static Review getReview(ApprovedStatus approvedStatus) {
		return Review.builder()
			.id(1L)
			.content("content")
			.approvedStatus(approvedStatus)
			.build();
	}
}
