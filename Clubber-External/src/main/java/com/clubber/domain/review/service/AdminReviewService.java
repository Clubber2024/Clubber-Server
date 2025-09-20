package com.clubber.domain.review.service;

import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewReply;
import com.clubber.domain.domains.review.implement.ReviewReader;
import com.clubber.domain.domains.review.repository.ReviewReplyRepository;
import com.clubber.domain.review.dto.CreateReviewApplyRequest;
import com.clubber.domain.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewReader reviewReader;
    private final AdminReader adminReader;
    private final ReviewMapper reviewMapper;
    private final ReviewReplyRepository reviewReplyRepository;

    @Transactional
    public void createReviewApply(Long reviewId, CreateReviewApplyRequest request) {
        Review review = reviewReader.findById(reviewId);
        Admin admin = adminReader.getCurrentAdmin();
        ReviewReply reviewApply = reviewMapper.toReviewApply(admin, review, request.content());
        reviewReplyRepository.save(reviewApply);
    }
}
