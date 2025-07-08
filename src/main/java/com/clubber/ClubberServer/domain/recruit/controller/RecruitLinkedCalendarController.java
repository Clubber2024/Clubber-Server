package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.CreateLinkedCalenderResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitLinkedCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins/calendars/link")
@RequiredArgsConstructor
@Tag(name = "[관리자 연동 캘린더 관련 API]")
public class RecruitLinkedCalendarController {

    private final RecruitLinkedCalendarService recruitLinkedCalendarService;

    @Operation(summary = "연동 캘린더 생성(모집글 생성 이후 호출)")
    @PostMapping
    public CreateLinkedCalenderResponse createLinkedCalender(@RequestBody CreateLinkedCalendarRequest request) {
        return recruitLinkedCalendarService.createLinkedCalender(request);
    }

    @Operation(summary = "특정 캘린더 연동 해제")
    @PatchMapping("/{id}/unlink")
    public void unlinkCalendar(@PathVariable Long id) {
        recruitLinkedCalendarService.unlinkCalendar(id);
    }
}
