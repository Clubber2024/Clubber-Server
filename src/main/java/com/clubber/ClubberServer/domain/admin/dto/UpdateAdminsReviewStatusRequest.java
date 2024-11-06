package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdminsReviewStatusRequest {

    @Size(min = 1, message = "1개 이상 수정해야합니다")
    @Size(max = 10, message = "10개 이하로 수정해야합니다.")
    @Schema(description = "승인 / 거절 하려는 리뷰 id 목록", example = "[1,2,3]")
    private List<Long> reviewIds;

    @NotNull
    @Schema(description = "승인(APPROVED) 혹은 거절(REJECTED)", example = "APPROVED")
    private ApprovedStatus approvedStatus;
}
