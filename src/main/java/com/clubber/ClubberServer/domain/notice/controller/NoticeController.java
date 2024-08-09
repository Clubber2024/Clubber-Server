package com.clubber.ClubberServer.domain.notice.controller;

import com.clubber.ClubberServer.domain.notice.dto.GetNoticeResponse;
import com.clubber.ClubberServer.domain.notice.repository.NoticeRepository;
import com.clubber.ClubberServer.domain.notice.service.NoticeService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="[공지사항]")
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;

    @DisableSwaggerSecurity
    @Operation(summary="공지사항 조회")
    @GetMapping("/v1/notices")
    public PageResponse<GetNoticeResponse> getNotices(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        PageResponse<GetNoticeResponse> notices=noticeService.getNotices(pageable);
        return notices;
    }

    @DisableSwaggerSecurity
    @Operation(summary="개별 공지사항 조회")
    @GetMapping("/v1/notices/{noticeId}")
    public GetNoticeResponse getNoticesDetail(@PathVariable("noticeId")Long noticeId)
    {
        return noticeService.getNoticesDetail(noticeId);
    }


}
