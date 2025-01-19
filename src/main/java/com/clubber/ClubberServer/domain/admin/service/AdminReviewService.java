package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.review.mapper.ReviewMapper;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminPendingReviewsSliceResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsPendingReviews;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusRequest;
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
	private final ReviewMapper reviewMapper;

	@Transactional(readOnly = true)
	public List<GetAdminsPendingReviews> getAdminPendingReviews() {
		Admin admin = adminReadService.getAdmin();
		List<Review> reviews = reviewRepository.findByApprovedStatusAndClubOrderByIdDesc(
			ApprovedStatus.PENDING, admin.getClub());

		return reviewMapper.getGetAdminPendingReviewList(reviews);
	}

	@Transactional
	public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewsApprovedStatus(
		UpdateAdminsReviewApprovedStatusRequest updateAdminsReviewApprovedStatusRequest) {
		Admin admin = adminReadService.getAdmin();

		List<Long> updateReviewRequestIds = updateAdminsReviewApprovedStatusRequest.getReviewIds();
		ApprovedStatus updateReviewApprovedStatus = updateAdminsReviewApprovedStatusRequest.getApprovedStatus();

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
		return reviewMapper.getGetAdminReviewsResponse(admin, club, reviews);
	}

	@Transactional(readOnly = true)
	public GetAdminPendingReviewsSliceResponse getAdminPendingReviewsWithSliceResponse(
		Pageable pageable, Long lastReviewId) {
		Admin admin = adminReadService.getAdmin();
		Club club = clubRepository.findClubByIdAndIsDeleted(admin.getClub().getId(), false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable,
			lastReviewId,
			ApprovedStatus.PENDING);
		return GetAdminPendingReviewsSliceResponse.of(reviews, pageable);
	}

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
}