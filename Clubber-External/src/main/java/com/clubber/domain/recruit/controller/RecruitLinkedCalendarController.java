package com.clubber.domain.recruit.controller;

import com.clubber.domain.recruit.dto.CreateLinkedCalendarRequest;
import com.clubber.domain.recruit.dto.CreateLinkedCalendarResponse;
import com.clubber.domain.recruit.service.RecruitLinkedCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins/calendars/link")
@RequiredArgsConstructor
@Tag(name = "[관리자 모집글 - 캘린더 연동 관련 API]")
public class RecruitLinkedCalendarController {

    private final RecruitLinkedCalendarService recruitLinkedCalendarService;

    @Operation(summary = "연동 캘린더 생성(모집글 생성 이후 호출)")
    @PostMapping
    public CreateLinkedCalendarResponse createLinkedCalender(@RequestBody CreateLinkedCalendarRequest request) {
        return recruitLinkedCalendarService.createLinkedCalendar(request);
    }

    @Operation(summary = "특정 캘린더 연동 해제")
    @PatchMapping("/{id}/unlink")
    public void unlinkCalendar(@PathVariable Long id) {
        recruitLinkedCalendarService.unlinkCalendar(id);
    }
}
