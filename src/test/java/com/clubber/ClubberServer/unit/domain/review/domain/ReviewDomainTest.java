package com.clubber.ClubberServer.unit.domain.review.domain;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.APPROVED;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewDomainTest {

	private static Review getReview(ApprovedStatus approvedStatus) {
		return Review.builder()
			.id(1L)
			.content("content")
			.approvedStatus(approvedStatus)
			.build();
	}

	@Test
	@DisplayName("승인 상태의 댓글이 아니라면 사용자들에게 보여주는 content는 null이 반환된다.")
	void getPendingReviewContentForUser() {
		//given
		ApprovedStatus[] approvedStatuses = ApprovedStatus.values();

		//when
		List<ApprovedStatus> approvedStatusExceptApproved = Arrays.stream(approvedStatuses)
			.filter(approvedStatus -> approvedStatus != APPROVED)
			.collect(Collectors.toList());

		//then
		assertAll(
			approvedStatusExceptApproved.stream()
				.map(
					approvedStatus -> {
						Review review = getReview(approvedStatus);
						return () -> assertNull(review.getContentForUser());
					})
		);
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
}
