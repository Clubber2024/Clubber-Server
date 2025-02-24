package com.clubber.ClubberServer.integration.domain.admin.service;

import static com.clubber.ClubberServer.integration.util.fixture.AdminReviewFixture.UPDATE_ADMIN_NOT_FOUND_REVIEW_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.AdminReviewFixture.VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_APPROVE;
import static com.clubber.ClubberServer.integration.util.fixture.AdminReviewFixture.VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_REJECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminReviewService;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.exception.UserReviewsNotFoundException;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminReviewServiceTest extends ServiceTest {

	@Autowired
	private AdminReviewService adminReviewService;

	@Autowired
	private ReviewRepository reviewRepository;

	@DisplayName("리뷰 승인을 수행한다")
	@WithMockCustomUser
	@Test
	void updateAdminsReviewApprove() {
		UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewApprovedStatusResponse = adminReviewService.updateAdminsReviewsApprovedStatus(
			VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_APPROVE);

		List<Review> approvedReviews = reviewRepository.findAllById(
			updateAdminsReviewApprovedStatusResponse.getReviewIds());

		assertThat(approvedReviews)
			.allMatch(review -> review.getApprovedStatus() == ApprovedStatus.APPROVED);
	}

	@DisplayName("리뷰 거절을 수행한다")
	@WithMockCustomUser
	@Test
	void updateAdminsReviewReject() {
		UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewApprovedStatusResponse = adminReviewService.updateAdminsReviewsApprovedStatus(
			VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_REJECT);

		List<Review> approvedReviews = reviewRepository.findAllById(
			updateAdminsReviewApprovedStatusResponse.getReviewIds());

		assertThat(approvedReviews)
			.allMatch(review -> review.getApprovedStatus() == ApprovedStatus.REJECTED);
	}

	@DisplayName("존재하지 않는 리뷰를 승인/거절하는 경우 예외가 발생한다.")
	@WithMockCustomUser
	@Test
	void updateAdminsNotFoundReviews() {
		assertThrows(UserReviewsNotFoundException.class,
			() -> adminReviewService.updateAdminsReviewsApprovedStatus(
				UPDATE_ADMIN_NOT_FOUND_REVIEW_REQUEST));
	}
}
