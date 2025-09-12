package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminPendingReviewsSliceResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsPendingReviews;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewVerifyResponse;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.admin.mapper.AdminReviewMapper;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.VerifiedStatus;
import com.clubber.ClubberServer.domain.review.exception.ReviewClubNotMatchException;
import com.clubber.ClubberServer.domain.review.exception.ReviewNotFoundException;
import com.clubber.ClubberServer.domain.review.exception.UserReviewsNotFoundException;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

	private final ReviewRepository reviewRepository;
	private final AdminReader adminReader;
	private final ClubRepository clubRepository;
	private final AdminReviewMapper adminReviewMapper;

	@Transactional(readOnly = true)
	public List<GetAdminsPendingReviews> getAdminPendingReviews() {
		Admin admin = adminReader.getCurrentAdmin();
		List<Review> reviews = reviewRepository.findByApprovedStatusAndClubOrderByIdDesc(
			ApprovedStatus.PENDING, admin.getClub());

		return adminReviewMapper.getGetAdminPendingReviewList(reviews);
	}

	private static void validateReviewExistence(List<Review> findReviews, List<Long> reviewIds) {
		if (findReviews.size() != reviewIds.size()) {
			throw UserReviewsNotFoundException.EXCEPTION;
		}
	}

	private static void validateReviewClub(Review review, Admin admin) {
		if (!admin.getClub().getId().equals(review.getClub().getId())) {
			throw ReviewClubNotMatchException.EXCEPTION;
		}
	}

	@Transactional
	public UpdateAdminsReviewVerifyResponse updateAdminsReviewVerify(Long reviewId) {
		Admin admin = adminReader.getCurrentAdmin();
		Review review = reviewRepository.findByIdAndNotDeletedApprovedStatus(reviewId)
			.orElseThrow(() -> ReviewNotFoundException.EXCEPTION);
		validateReviewClub(review, admin);
		review.verify();
		return UpdateAdminsReviewVerifyResponse.of(review, admin);
	}

	@Transactional(readOnly = true)
	public GetAdminsReviewsResponse getAdminsReviews(Pageable pageable,
		ApprovedStatus approvedStatus, VerifiedStatus verifiedStatus) {
		Admin admin = adminReader.getCurrentAdmin();
		Club club = clubRepository.findClubByIdAndIsDeleted(admin.getClub().getId(), false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		Page<Review> reviews = reviewRepository.queryReviewByClub(club, pageable, approvedStatus, verifiedStatus);
		return adminReviewMapper.getGetAdminReviewsResponse(admin, club, reviews);
	}

	@Transactional(readOnly = true)
	public GetAdminPendingReviewsSliceResponse getAdminPendingReviewsWithSliceResponse(
		Pageable pageable, Long lastReviewId) {
		Admin admin = adminReader.getCurrentAdmin();
		Club club = clubRepository.findClubByIdAndIsDeleted(admin.getClub().getId(), false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable,
			lastReviewId,
			ApprovedStatus.PENDING);
		return adminReviewMapper.getGetAdminPendingReviewSliceResponse(reviews, pageable);
	}
}