package com.clubber.domain.domains.report.domain;

import com.clubber.common.mapper.enums.EnumDefaultMapperType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportReason implements EnumDefaultMapperType {
    ABUSE("욕설 및 비방"),
    FALSE_INFORMATION("허위 정보"),
    ADVERTISEMENT_SPAM("광고 및 스팸"),
    PERSONAL_INFORMATION("개인정보 포함"),
    OTHER("기타");

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
