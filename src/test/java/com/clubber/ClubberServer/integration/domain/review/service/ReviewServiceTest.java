package com.clubber.ClubberServer.integration.domain.review.service;

import static com.clubber.ClubberServer.integration.util.fixture.ReviewFixture.VALID_REVIEW_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewRequest;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewResponse;
import com.clubber.ClubberServer.domain.review.exception.UserAlreadyReviewedException;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import java.util.Optional;

import com.clubber.ClubberServer.integration.util.fixture.ReviewFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class ReviewServiceTest extends ServiceTest {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ReviewRepository reviewRepository;

	@DisplayName("리뷰 작성에 성공한다")
	@WithMockCustomUser(second = "USER")
	@Test
	void createReviewSuccess() {
		/**
		 * 1번 user club1에 리뷰 등록되어있음
		 */
		CreateClubReviewRequest createClubReviewRequest = ReviewFixture.getDefaultCreateClubReviewRequestBuilder().sample();
		CreateClubReviewResponse reviewCreateResponse = reviewService.createReview(2L, createClubReviewRequest);

		Optional<Review> createdReview = reviewRepository.findById(
			reviewCreateResponse.getReviewId());

		assertAll(
			() -> assertThat(createdReview).isNotNull(),
			() -> assertThat(createdReview.get().getApprovedStatus()).isEqualTo(
				ApprovedStatus.PENDING),
			() -> assertThat(createdReview.get().getContent()).isEqualTo(
				VALID_REVIEW_CREATE_REQUEST.getContent())
			// () -> assertThat(ReviewKeyword.from(createdReview.get().getReviewKeywords())).isEqualTo(VALID_REVIEW_CREATE_REQUEST.getKeywords())
		);
	}

	@DisplayName("리뷰_중복작성시_예외발생")
	@WithMockCustomUser(second = "USER")
	@Test
	void createDuplicateReview() {
		assertThrows(UserAlreadyReviewedException.class,
			() -> reviewService.createReview(1L, VALID_REVIEW_CREATE_REQUEST));
	}
}
