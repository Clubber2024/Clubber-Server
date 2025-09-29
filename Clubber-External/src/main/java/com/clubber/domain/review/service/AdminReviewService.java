package com.clubber.domain.review.service;

import com.clubber.domain.admin.dto.GetAdminPendingReviewsSliceResponse;
import com.clubber.domain.admin.dto.GetAdminsPendingReviews;
import com.clubber.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.domain.admin.dto.GetReviewEnabledStatus;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.admin.mapper.AdminReviewMapper;
import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.club.exception.ClubNotFoundException;
import com.clubber.domain.domains.club.repository.ClubRepository;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewReply;
import com.clubber.domain.domains.review.domain.ReviewSortType;
import com.clubber.domain.domains.review.implement.ReviewReader;
import com.clubber.domain.domains.review.implement.ReviewValidator;
import com.clubber.domain.domains.review.repository.ReviewReplyRepository;
import com.clubber.domain.domains.review.repository.ReviewRepository;
import com.clubber.domain.domains.review.vo.ClubReviewResponse;
import com.clubber.domain.review.dto.CreateReviewApplyRequest;
import com.clubber.domain.review.dto.GetClubReviewAgreedStatusResponse;
import com.clubber.domain.review.mapper.ReviewMapper;
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
    private final ReviewReader reviewReader;
    private final ReviewReplyRepository reviewReplyRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewValidator reviewValidator;

    //TODO 인증 이미지 포함되어야하는지
    @Transactional(readOnly = true)
    public List<GetAdminsPendingReviews> getAdminPendingReviews() {
        Admin admin = adminReader.getCurrentAdmin();
        List<Review> reviews = reviewRepository.findByClubOrderByIdDesc(admin.getClub());
        return adminReviewMapper.getGetAdminPendingReviewList(reviews);
    }

//    @Transactional(readOnly = true)
//    public GetAdminsReviewsResponse getAdminsReviews(Pageable pageable,
//                                                     ReviewSortType sortType) {
//        Admin admin = adminReader.getCurrentAdmin();
//        Club club = admin.getClub();
//        Page<ClubReviewResponse> clubReviewResponses = reviewRepository.queryReviewByClub(club, pageable, sortType);
//        return adminReviewMapper.getGetAdminReviewsResponse(admin, club, clubReviewResponses);
//    }

//    @Transactional(readOnly = true)
//    public GetAdminPendingReviewsSliceResponse getAdminPendingReviewsWithSliceResponse(
//            Pageable pageable, Long lastReviewId) {
//        Admin admin = adminReader.getCurrentAdmin();
//        Club club = clubRepository.findClubByIdAndIsDeleted(admin.getClub().getId(), false)
//                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
//
//        List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable,
//                lastReviewId);
//        return adminReviewMapper.getGetAdminPendingReviewSliceResponse(reviews, pageable);
//    }

    @Transactional
    public void createReviewApply(Long reviewId, CreateReviewApplyRequest request) {
        Review review = reviewReader.findById(reviewId);
        Admin admin = adminReader.getCurrentAdmin();
        reviewValidator.validateReviewClub(admin.getClub(), review);
        ReviewReply reviewApply = reviewMapper.toReviewApply(admin, review, request.content());
        reviewReplyRepository.save(reviewApply);
    }

    @Transactional
    public void disableClubReview() {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();
        club.disableReview();
    }
    @Transactional
    public void enableClubReview() {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();
        club.enableReview();
    }

    @Transactional(readOnly = true)
    public GetClubReviewAgreedStatusResponse getReviewEnabledStatus() {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();
        return GetClubReviewAgreedStatusResponse.from(club);
    }
}