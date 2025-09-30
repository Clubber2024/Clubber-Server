package com.clubber.domain.review.dto;

import com.clubber.domain.domains.report.domain.ReportReason;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewReportRequest {

    @Schema(description = "신고 사유", example = "ABUSE")
    @NotNull(message = "신고 사유를 선택해주세요")
    private ReportReason reportReason;

    @Schema(description = "기타 선택시, 직접 입력", example = "잘못된 내용 기재")
    private String detailReason;

}
