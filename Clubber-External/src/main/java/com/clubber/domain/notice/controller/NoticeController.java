package com.clubber.domain.notice.controller;

import com.clubber.domain.notice.dto.GetNoticeResponse;
import com.clubber.domain.notice.dto.GetNoticesAtMainResponse;
import com.clubber.domain.notice.service.NoticeService;
import com.clubber.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.global.common.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name="[공지사항]")
public class NoticeController {
    private final NoticeService noticeService;


    @DisableSwaggerSecurity
    @Operation(summary="메인 페이지에서 공지사항 조회")
    @GetMapping("/notices/main-page")
    public List<GetNoticesAtMainResponse> getNoticesAtMain(){
        return noticeService.getNoticesAtMain();
    }

    @DisableSwaggerSecurity
    @Operation(summary="공지사항 조회")
    @GetMapping("/notices")
    public PageResponse<GetNoticeResponse> getNotices(@PageableDefault(size = 10) Pageable pageable){
        PageResponse<GetNoticeResponse> notices=noticeService.getNotices(pageable);
        return notices;
    }

    @DisableSwaggerSecurity
    @Operation(summary="개별 공지사항 조회")
    @GetMapping("/notices/{noticeId}")
    public GetNoticeResponse getNoticesDetail(@PathVariable("noticeId")Long noticeId)
    {
        return noticeService.getNoticesDetail(noticeId);
    }


}
