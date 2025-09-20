package com.clubber.domain.admin.service;

import com.clubber.domain.admin.dto.GetAdminPendingReviewsSliceResponse;
import com.clubber.domain.admin.dto.GetAdminsPendingReviews;
import com.clubber.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.domain.admin.dto.UpdateAdminsReviewVerifyResponse;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.admin.mapper.AdminReviewMapper;
import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.club.exception.ClubNotFoundException;
import com.clubber.domain.domains.club.repository.ClubRepository;
import com.clubber.domain.domains.review.domain.DeletionStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewSortType;
import com.clubber.domain.domains.review.exception.ReviewClubNotMatchException;
import com.clubber.domain.domains.review.exception.ReviewNotFoundException;
import com.clubber.domain.domains.review.exception.UserReviewsNotFoundException;
import com.clubber.domain.domains.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewRepository reviewRepository;
    private final AdminReader adminReader;
    private final ClubRepository clubRepository;
    private final AdminReviewMapper adminReviewMapper;

    //TODO 인증 이미지 포함되어야하는지
    @Transactional(readOnly = true)
    public List<GetAdminsPendingReviews> getAdminPendingReviews() {
        Admin admin = adminReader.getCurrentAdmin();
        List<Review> reviews = reviewRepository.findByClubOrderByIdDesc(admin.getClub());
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
        return UpdateAdminsReviewVerifyResponse.of(review, admin);
    }

    @Transactional(readOnly = true)
    public GetAdminsReviewsResponse getAdminsReviews(Pageable pageable,
                                                     ReviewSortType sortType) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = clubRepository.findClubByIdAndIsDeleted(admin.getClub().getId(), false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
        Page<Review> reviews = reviewRepository.queryReviewByClub(club, pageable, sortType);
        return adminReviewMapper.getGetAdminReviewsResponse(admin, club, reviews);
    }

    @Transactional(readOnly = true)
    public GetAdminPendingReviewsSliceResponse getAdminPendingReviewsWithSliceResponse(
            Pageable pageable, Long lastReviewId) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = clubRepository.findClubByIdAndIsDeleted(admin.getClub().getId(), false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable,
                lastReviewId);
        return adminReviewMapper.getGetAdminPendingReviewSliceResponse(reviews, pageable);
    }
}