package com.clubber.domain.report.controller;

import com.clubber.common.vo.enums.EnumMapperVO;
import com.clubber.domain.domains.report.vo.GetOneReportResponse;
import com.clubber.domain.report.dto.GetNextAlwaysReportRequest;
import com.clubber.domain.report.dto.GetOneReportDetailResponse;
import com.clubber.domain.report.dto.PutReportRequest;
import com.clubber.domain.report.service.ReportService;
import com.clubber.global.common.page.PageResponse;
import com.clubber.global.common.slice.SliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/reports")
@RequiredArgsConstructor
@Tag(name = "[리뷰 신고]")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "신고 리뷰 목록 조회")
    @GetMapping("")
    public PageResponse<GetOneReportResponse> getReviewReports(
        @PageableDefault(size = 10) Pageable pageable) {
        return reportService.getReviewReports(pageable);
    }

    @Operation(summary = "신고 리뷰 - 상세 조회")
    @PostMapping("/next-always")
    public SliceResponse<GetOneReportDetailResponse> getReportList(@RequestBody
    GetNextAlwaysReportRequest request) {
        return reportService.getNextReport(request);
    }

    @Operation(summary = "리뷰 신고 승인/거절")
    @PostMapping
    public void putReportStatus(@RequestBody @Valid PutReportRequest request) {
        reportService.postReportStatus(request);
    }

    @Operation(summary = "리뷰 처리 상태 목록")
    @GetMapping(value = "/status")
    public List<EnumMapperVO> getReportStatus() {
        return reportService.getReportStatus();
    }
}
