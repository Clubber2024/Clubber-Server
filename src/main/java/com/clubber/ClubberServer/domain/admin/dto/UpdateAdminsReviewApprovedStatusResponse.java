package com.clubber.ClubberServer.domain.admin.dto;


import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAdminsReviewApprovedStatusResponse {
    private final Long adminId;
    private final Long reviewId;
    private final ApprovedStatus approvedStatus;

    public static UpdateAdminsReviewApprovedStatusResponse of(Admin admin, Review review, ApprovedStatus approvedStatus){
        return UpdateAdminsReviewApprovedStatusResponse.builder()
                .adminId(admin.getId())
                .reviewId(review.getId())
                .approvedStatus(approvedStatus)
                .build();
    }
}
