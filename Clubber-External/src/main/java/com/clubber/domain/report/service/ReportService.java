package com.clubber.domain.report.service;

import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.common.vo.enums.EnumMapperVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EnumMapper enumMapper;

    public List<EnumMapperVO> getReportReasons() {
        return enumMapper.get("ReportReason");
    }

}
