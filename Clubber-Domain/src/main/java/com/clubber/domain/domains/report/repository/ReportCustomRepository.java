package com.clubber.domain.domains.report.repository;

import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.report.vo.GetOneReportResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportCustomRepository {

    List<Report> findNextReport(Long reportId, Long reviewId);

    Page<GetOneReportResponse> findReports(Pageable pageable);

}
