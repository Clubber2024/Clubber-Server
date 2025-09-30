package com.clubber.domain.domains.report.domain;

import com.clubber.common.mapper.enums.EnumDefaultMapperType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportStatus implements EnumDefaultMapperType {
    PENDING("대기"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

}
