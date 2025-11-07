package com.clubber.domain.report.dto;

import jakarta.validation.constraints.NotNull;

public record GetNextAlwaysReportRequest (
    @NotNull(message = "리뷰 id를 입력해주세요")
    Long reviewId,
    Long nowReportId
){
}
