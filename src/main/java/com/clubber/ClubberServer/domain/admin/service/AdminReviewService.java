package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewByStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewStatusRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.exception.ReviewClubNotMatchException;
import com.clubber.ClubberServer.domain.review.exception.UserReviewsNotFoundException;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
    private final ReviewRepository reviewRepository;
    private final AdminRepository adminRepository;
    private final ClubRepository clubRepository;

    @Transactional(readOnly = true)
    public List<GetAdminsReviewByStatusResponse> getAdminReviewsByApprovedStatus(){
        Long adminId = SecurityUtils.getCurrentUserId();
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
        List<Review> reviews = reviewRepository.findByApprovedStatusAndClubOrderByIdDesc(
                ApprovedStatus.PENDING, admin.getClub());

        return GetAdminsReviewByStatusResponse.from(reviews);
    }

//    @Transactional
//    public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewApprove(Long reviewId) {
//        Long currentUserId = SecurityUtils.getCurrentUserId();
//        Admin admin = adminRepository.findById(currentUserId)
//                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
//
//        Review review = reviewRepository.findById(reviewId).get();
//        if(!admin.getClub().getId().equals(review.getClub().getId()))
//            throw ReviewClubNotMatchException.EXCEPTION;
//
//        review.approve();
//        return UpdateAdminsReviewApprovedStatusResponse.of(admin, review, ApprovedStatus.APPROVED);
//    }
@Transactional
public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewApprove(
        UpdateAdminsReviewStatusRequest updateAdminsReviewStatusRequest) {

    List<Long> reviewIds = updateAdminsReviewStatusRequest.getReviewIds();

    Long currentUserId = SecurityUtils.getCurrentUserId();
    Admin admin = adminRepository.findById(currentUserId)
            .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

    ApprovedStatus approvedStatus = updateAdminsReviewStatusRequest.getApprovedStatus();
    for (Long reviewId : reviewIds) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> UserReviewsNotFoundException.EXCEPTION);

        if(!admin.getClub().getId().equals(review.getClub().getId()))
            throw ReviewClubNotMatchException.EXCEPTION;

        if(approvedStatus == ApprovedStatus.APPROVED)
            review.approve();
        else if (approvedStatus == ApprovedStatus.REJECTED) {
            review.reject();
        }
    }
    return UpdateAdminsReviewApprovedStatusResponse.of(admin, reviewIds, approvedStatus);
}

//    @Transactional
//    public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewReject(Long reviewId) {
//        Long currentUserId = SecurityUtils.getCurrentUserId();
//        Admin admin = adminRepository.findById(currentUserId)
//                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
//
//        Review review = reviewRepository.findById(reviewId).get();
//        if(!admin.getClub().getId().equals(review.getClub().getId()))
//            throw ReviewClubNotMatchException.EXCEPTION;
//        review.reject();
//        return UpdateAdminsReviewApprovedStatusResponse.of(admin, review, ApprovedStatus.REJECTED);
//    }

    @Transactional(readOnly = true)
    public GetAdminsReviewsResponse getAdminsReviews() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
        Club club = clubRepository.findById(admin.getClub().getId())
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
        List<Review> reviews = reviewRepository.queryReviewByClub(club);
        return GetAdminsReviewsResponse.of(admin, club, reviews);
    }

}