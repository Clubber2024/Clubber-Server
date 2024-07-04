package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
    private final ReviewRepository reviewRepository;
    private final AdminRepository adminRepository;
    public void getAdminReviewsByApprovedStatus(ApprovedStatus approvedStatus){
        Long adminId = SecurityUtils.getCurrentUserId();
        Admin admin = adminRepository.findById(adminId).get();
        List<Review> reviews = reviewRepository.findByApprovedStatusAndClub(
                approvedStatus, admin.getClub());
    }
}
