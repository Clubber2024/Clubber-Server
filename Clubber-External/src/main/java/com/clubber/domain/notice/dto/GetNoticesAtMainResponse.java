package com.clubber.domain.notice.dto;

import com.clubber.domain.notice.domain.Notice;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNoticesAtMainResponse {

    @Schema(description = "공지사항 id", example = "1")
    private final Long noticeId;

    @Schema(description = "공지사항 제목", example = "버그 수정 완료")
    private final String title;

    @Schema(description = "공지사항 생성 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    public static GetNoticesAtMainResponse from(Notice notice){
        return  GetNoticesAtMainResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
