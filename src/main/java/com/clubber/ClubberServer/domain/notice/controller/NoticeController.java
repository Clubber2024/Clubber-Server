package com.clubber.ClubberServer.domain.notice.controller;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.notice.dto.NoticesDto;
import com.clubber.ClubberServer.domain.notice.repository.NoticeRepository;
import com.clubber.ClubberServer.domain.notice.service.NoticeService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/v1/notice")
    public List<NoticesDto> getNotices(){
        List<NoticesDto> notices=noticeService.getSortedNotices();
        return notices;
    }


}
