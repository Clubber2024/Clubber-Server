package com.clubber.ClubberServer.domain.admin.controller;


import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewByStatusResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminReviewService;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/reviews")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @GetMapping
    public List<GetAdminsReviewByStatusResponse> getAdminReviewsByApprovedStatus(@RequestParam ApprovedStatus approvedStatus){
        return adminReviewService.getAdminReviewsByApprovedStatus(approvedStatus);
    }
}
