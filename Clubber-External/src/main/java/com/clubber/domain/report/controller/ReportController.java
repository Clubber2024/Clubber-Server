package com.clubber.domain.report.controller;

import com.clubber.common.vo.enums.EnumMapperVO;
import com.clubber.domain.report.service.ReportService;
import com.clubber.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@Tag(name = "[리뷰 신고]")
public class ReportController {

    private final ReportService reportService;

    @DisableSwaggerSecurity
    @Operation(summary = "신고 사유 목록")
    @GetMapping(value = "/reasons")
    public List<EnumMapperVO> getReportReasons() {
        return reportService.getReportReasons();
    }

}
