package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAdminsReviewApprovedStatusResponse {
    @Schema(description = "동아리 계정 id", example = "1")
    private final Long adminId;

    @Schema(description = "승인/미승인된 리뷰 id", example = "[1,2,3]")
    private final List<Long> reviewIds;

    @Schema(description = "승인/미승인 중 처리된 상태", example = "APPROVED")
    private final ApprovedStatus approvedStatus;

    public static UpdateAdminsReviewApprovedStatusResponse of(Admin admin, List<Long> reviewIds, ApprovedStatus approvedStatus){
        return UpdateAdminsReviewApprovedStatusResponse.builder()
                .adminId(admin.getId())
                .reviewIds(reviewIds)
                .approvedStatus(approvedStatus)
                .build();
    }
}
