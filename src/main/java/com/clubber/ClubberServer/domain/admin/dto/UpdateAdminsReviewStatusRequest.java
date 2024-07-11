package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAdminsReviewStatusRequest {

    @Size(min = 1, message = "1개 이상 수정해야합니다")
    @Schema(description = "승인/미승인 하려는 리뷰 id 목록", example = "[1,2,3]")
    private List<Long> reviewIds;

    private ApprovedStatus approvedStatus;
}