package com.clubber.ClubberServer.domain.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminPendingReviewsWithSliceResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewByStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewStatusRequest;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.exception.ReviewClubNotMatchException;
import com.clubber.ClubberServer.domain.review.exception.UserReviewsNotFoundException;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

	private final ReviewRepository reviewRepository;
	private final AdminReadService adminReadService;
	private final ClubRepository clubRepository;

	private static void validateReviewClub(Review review, Admin admin) {
		if (!admin.getClub().getId().equals(review.getClub().getId())) {
			throw ReviewClubNotMatchException.EXCEPTION;
		}
	}

	private static void validateReviewExistence(List<Review> findReviews, List<Long> reviewIds) {
		if (findReviews.size() != reviewIds.size()) {
			throw UserReviewsNotFoundException.EXCEPTION;
		}
	}

	@Transactional(readOnly = true)
	public List<GetAdminsReviewByStatusResponse> getAdminReviewsByApprovedStatus() {
		Admin admin = adminReadService.getAdmin();
		List<Review> reviews = reviewRepository.findByApprovedStatusAndClubOrderByIdDesc(
			ApprovedStatus.PENDING, admin.getClub());

		return GetAdminsReviewByStatusResponse.from(reviews);
	}

	@Transactional
	public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewsApprovedStatus(
		UpdateAdminsReviewStatusRequest updateAdminsReviewStatusRequest) {
		Admin admin = adminReadService.getAdmin();

		List<Long> updateReviewRequestIds = updateAdminsReviewStatusRequest.getReviewIds();
		ApprovedStatus updateReviewApprovedStatus = updateAdminsReviewStatusRequest.getApprovedStatus();

		List<Review> findReviews = reviewRepository.findAllById(updateReviewRequestIds);
		validateReviewExistence(findReviews, updateReviewRequestIds);

		for (Review review : findReviews) {
			validateReviewClub(review, admin);
			review.updateReviewStatus(updateReviewApprovedStatus);
		}
		return UpdateAdminsReviewApprovedStatusResponse.of(admin, updateReviewRequestIds,
			updateReviewApprovedStatus);
	}

	@Transactional(readOnly = true)
	public GetAdminsReviewsResponse getAdminsReviews(Pageable pageable) {
		Admin admin = adminReadService.getAdmin();
		Club club = clubRepository.findClubByIdAndIsDeleted(admin.getClub().getId(), false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		Page<Review> reviews = reviewRepository.queryReviewByClub(club, pageable);
		return GetAdminsReviewsResponse.of(admin, club, reviews);
	}

	@Transactional(readOnly = true)
	public GetAdminPendingReviewsWithSliceResponse getAdminPendingReviewsWithSliceResponse(
		Pageable pageable, Long lastReviewId) {
		Admin admin = adminReadService.getAdmin();
		Club club = clubRepository.findClubByIdAndIsDeleted(admin.getClub().getId(), false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable,
			lastReviewId,
			ApprovedStatus.PENDING);
		return GetAdminPendingReviewsWithSliceResponse.of(reviews, pageable);
	}
}