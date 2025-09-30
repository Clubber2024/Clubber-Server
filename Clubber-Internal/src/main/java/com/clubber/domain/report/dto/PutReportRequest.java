package com.clubber.domain.report.dto;


import com.clubber.domain.domains.report.domain.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PutReportRequest (
        @NotNull(message = "리뷰 id를 입력해주세요")
        Long reviewId,
        @NotNull
        @Schema(description = "승인/거절", example = "APPROVED")
        ReportStatus reportStatus
) {
}