package com.clubber.ClubberServer.domain.notice.controller;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.notice.dto.NoticesDto;
import com.clubber.ClubberServer.domain.notice.repository.NoticeRepository;
import com.clubber.ClubberServer.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;


    @GetMapping("/v1/notice")
    public ResponseEntity<List<NoticesDto>> getNotices(){
        List<NoticesDto> notices=noticeService.getSortedNotices();
        return ResponseEntity.ok(notices);
    }


}
